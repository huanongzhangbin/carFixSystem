package com.example.carfixsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.carfixsystem.entity.Employee;
import com.example.carfixsystem.entity.Fitting;
import com.example.carfixsystem.entity.FittingType;
import com.example.carfixsystem.entity.FixRecord;
import com.example.carfixsystem.service.*;
import com.example.carfixsystem.util.JSONResult;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张彬
 * @since 2022-01-26
 */
@Slf4j
@RestController
@RequestMapping("/fixRecord")
public class FixRecordController {

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IFixRecordService fixRecordService;

    @Autowired
    IFittingTypeService fittingTypeService;

    @Autowired
    IFittingService fittingService;

    @Autowired
    IOrderInfoService orderInfoService;

    @GetMapping("/getList")
    public JSONResult getList(@RequestParam(value="query") String query,
                              @RequestParam(value = "pagenum") Integer current,
                              @RequestParam(value = "pagesize") Integer size,
                              @RequestParam(value = "showNotOut",defaultValue = "false") Boolean showNotOut,
                              @RequestParam(value="reserveOnline",required = false) Boolean reserveOnline){

        QueryWrapper<FixRecord> queryWrapper=new QueryWrapper<FixRecord>();
        log.info(showNotOut.toString());
        if(showNotOut){
            queryWrapper.eq("has_out",false);
        }

        if(reserveOnline!=null&&reserveOnline==true){
            queryWrapper.eq("reserve_online",true).eq("has_out",false);
        }
        if(!query.equals("")){
            queryWrapper.and(wq->wq.eq("username",query).or().eq("id",query));
           // queryWrapper.or(wq->wq.eq("username",query)).or(wq->wq.eq("id",query));
        }
        Page<FixRecord> fixRecordPage=new Page<FixRecord>(current,size);
        IPage<FixRecord> ipage= fixRecordService.page(fixRecordPage,queryWrapper);//这里可以写查询条件wrapper
        return JSONResult.success(ipage);
    }
    @PostMapping("/getReserveRecord/{carNumber}")
    public JSONResult getReserveRecord(@PathVariable String carNumber){

        QueryWrapper<FixRecord> queryWrapper=new QueryWrapper<FixRecord>();
        queryWrapper.and(wq->wq.eq("car_number",carNumber).isNotNull("reserve_time"));//.ne("reserve_time",null));
            // queryWrapper.or(wq->wq.eq("username",query)).or(wq->wq.eq("id",query));
    List<FixRecord> list=    fixRecordService.list(queryWrapper);
        list.sort(new Comparator<FixRecord>() {
            @Override
            public int compare(FixRecord o1, FixRecord o2) {
                return o1.getReserveTime().compareTo(o2.getReserveTime());
            }
        });
        return JSONResult.success(list);
    }

    @PostMapping("/addOrder")
    public JSONResult addOrder(@RequestBody FixRecord fixRecord){
        computeTotal(fixRecord);//设置一下total
       String employeeName= fixRecord.getEmployeeName();
       UpdateWrapper<Employee> updateWapper=new UpdateWrapper<>();
       updateWapper.eq("employee_name",employeeName).set("busy",true);
       employeeService.update(updateWapper);

        boolean success=  fixRecordService.save(fixRecord);
      if(success){
          return  JSONResult.success("订单添加成功");
      }
      return JSONResult.failMsg("订单添加失败");
    }
    @Transactional
    @PostMapping("/reserve")
    public JSONResult reserve(@RequestBody FixRecord fixRecord){
        String date=(fixRecord.getReserveTime().split(" "))[0];
        if(orderInfoService.decreaseRemain(date)){
          return   addOrder(fixRecord);
        }
        return JSONResult.failMsg("订单添加失败");
    }

    private void computeTotal(FixRecord fixRecord) {
        double total=0;
        if(fixRecord.getFittingNames()!=null){
            String[] fittingNames= fixRecord.getFittingNames().split(";");
            List<FittingType> fittingTypes=fittingTypeService.list();

            for(String f:fittingNames){
                for(FittingType fittingType:fittingTypes){
                    if(f.equals(fittingType.getFittingName())){
                        total+=fittingType.getPrice();
                        break;
                    }
                }
            }
        }
        fixRecord.setTotal(total);
    }
    @GetMapping("/deleteRecordById")
    public  JSONResult deleteRecordById(Integer id){
       boolean success= fixRecordService.removeById(id);
       if(success){
           return  JSONResult.success("删除成功");
       }else{
           return JSONResult.failMsg("删除失败");
       }
    }
    @GetMapping("/getRecordById")
    public JSONResult getRecordById(Integer id){
        FixRecord f= fixRecordService.getById(id);
        if(f!=null){
            return JSONResult.success(f);
        }
        return JSONResult.failMsg("获取订单信息失败");
    }
    @PostMapping("/editRecord")
    public JSONResult editRecord(@RequestBody FixRecord fixRecord){
        computeTotal(fixRecord);//设置一下total
      boolean success=  fixRecordService.updateById(fixRecord);
        if(success){
            return JSONResult.success("更新成功了");
        }else{
            return JSONResult.failMsg("更新失败了");
        }
      //  return  JSONResult.success("hhhhh");
    }


    @PostMapping("/doneOrder/{id}/{employeeName}")
    public JSONResult doneOrder(@PathVariable Long id,@PathVariable String employeeName){
        UpdateWrapper<FixRecord> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("done",true);

        UpdateWrapper<Employee> updateWrapper2=new UpdateWrapper<>();
        updateWrapper2.eq("employee_name",employeeName).set("busy",false);
        employeeService.update(updateWrapper2);
     boolean success=   fixRecordService.update(updateWrapper);
     if(success) return  JSONResult.success("修改状态成功");
     return JSONResult.failMsg("修改状态失败");
    }

    @GetMapping("/getNeedFittingNameList")
    public JSONResult getFittingListNeedRecord(Integer id){
        FixRecord fixRecord= fixRecordService.getById(id);
        String[] fittingNames= fixRecord.getFittingNames().split(";");
      List<FittingType> fittingTypes= fittingTypeService.list();
      StringBuffer result=new StringBuffer();
        for(String f:fittingNames){
            for(FittingType fittingType:fittingTypes){
                if(f.equals(fittingType.getFittingName())&&fittingType.getIsSave()){
                    result.append(f).append(";");
                }
            }
        }
        String r="";
        if(result.length()>0){
             r=result.substring(0,result.length()-1);
        }
        log.info(r);
        HashMap<String,String > res=new HashMap<>();
        res.put("fittingNames",r);
        return JSONResult.success(res);
    }

    @PostMapping("/outFitting/{id}/{carNumber}")
    public JSONResult finishOrder(@PathVariable("id")Integer id,@PathVariable("carNumber") String carNumber, @RequestBody(required = false) List<Fitting> usedFittingList){

        try {
            List<Fitting> res=new ArrayList<Fitting>();
            if(usedFittingList!=null){
                for(Fitting f:usedFittingList){
                    Fitting gf=fittingService.getById(f.getFittingId());
                    if(gf.getIsUsed() || !gf.getFittingName().equals(f.getFittingName())){
                        continue;
                    }
                    gf.setIsUsed(true);
                    gf.setCarNumber(carNumber);
                    res.add(gf);
                }
            }
            if (usedFittingList==null||res.size() == usedFittingList.size()) {
                UpdateWrapper<FixRecord> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("has_out", true);
                updateWrapper.eq("id", id);
                fixRecordService.update(updateWrapper);
                fittingService.updateBatchById(res);
                return JSONResult.success("出库成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.failMsg("出库失败");
        }
         return JSONResult.failMsg("出库失败");
    }
    @PostMapping("/getReserves/{carNumber}")
    public JSONResult getReserveInfo(@PathVariable String carNumber,@RequestBody List<String> dateList){
        QueryWrapper<FixRecord> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("car_number",carNumber);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String d=  simpleDateFormat.format(new Date());
        log.info(carNumber);
       queryWrapper.gt("reserve_time",d );
        List<FixRecord> list= fixRecordService.list(queryWrapper);
        log.info(list.toString());
        List<Boolean> reserves=new ArrayList<>();
            for(String s:dateList){
                boolean b=false;
                for(FixRecord f:list){
                    if(s.equals((f.getReserveTime().split(" "))[0])){//是日期eg.2022.2.24
                        b=true;
                        break;
                    }
                }
                reserves.add(b);
            }
           return JSONResult.success(reserves);
    }
}
