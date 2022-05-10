package com.example.carfixsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.carfixsystem.entity.Fitting;
import com.example.carfixsystem.entity.FittingType;
import com.example.carfixsystem.entity.FixRecord;
import com.example.carfixsystem.service.IFittingService;
import com.example.carfixsystem.service.IFittingTypeService;
import com.example.carfixsystem.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 张彬
 * @since 2022-01-26
 */
@Slf4j
@Controller
@RequestMapping("/fitting")
@RestController
public class FittingController {
    @Autowired
    IFittingService fittingService;
    @Autowired
    IFittingTypeService fittingTypeService;

    @PostMapping("/saveFittingList")
    public JSONResult saveFittingList(@RequestBody List<Fitting> list) {
        log.info(list.toString()+"----------");
        List<FittingType> fittingTypeList = fittingTypeService.list();
        HashSet<String> set = new HashSet<String>();
        for (FittingType ft : fittingTypeList) {
            set.add(ft.getFittingName());
        }
        List<Fitting> res = new ArrayList<>();
        for (Fitting f : list) {
            if (set.contains(f.getFittingName())) {
                f.setCarNumber("未使用");
                f.setIsUsed(false);
                res.add(f);
            }
        }
        try {
            boolean success = fittingService.saveOrUpdateBatch(res);
            if (success) return JSONResult.success("配件批量添加成功了");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONResult.failMsg("配件批量添加失败了");
    }

    @GetMapping("/getList")
    public JSONResult getList(@RequestParam(value = "query") String query, @RequestParam(value = "pagenum") Integer current, @RequestParam(value = "pagesize") Integer size, @RequestParam(value = "showNotOut", defaultValue = "false") Boolean showNotOut) {

        QueryWrapper<Fitting> queryWrapper = new QueryWrapper<Fitting>();
        log.info(showNotOut.toString());

        if (!query.equals("")) {
            log.info(query+"jjjjjjjj");
         //   queryWrapper.and(wq->wq.eq("username",query).or().eq("id",query));
            // queryWrapper.or(wq->wq.eq("username",query)).or(wq->wq.eq("id",query));
            queryWrapper.and(wq -> wq.eq("fitting_id", query).or().eq("car_number", query));
            // queryWrapper.or(wq->wq.eq("fitting_id",query)).or(wq->wq.eq("car_number",query));
        }
        Page<Fitting> fittingPage = new Page<Fitting>(current, size);
        IPage<Fitting> ipage = fittingService.page(fittingPage, queryWrapper);//这里可以写查询条件wrapper
            ipage.getRecords().sort(new Comparator<Fitting>() {
            @Override
            public int compare(Fitting o1, Fitting o2) {
               return o1.getFittingName().compareTo(o2.getFittingName());
            }
        });
        return JSONResult.success(ipage);
    }
    @GetMapping("/getFixRecord")
    public JSONResult getFixRecord(@RequestParam(value = "carNumber") String carNumber, @RequestParam(value = "pagenum") Integer current, @RequestParam(value = "pagesize") Integer size){
        QueryWrapper<Fitting> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("car_number",carNumber);
        Page<Fitting> fittingPage = new Page<Fitting>(current, size);
        IPage<Fitting> ipage = fittingService.page(fittingPage, queryWrapper);//这里可以写查询条件wrapper
        ipage.getRecords().sort(new Comparator<Fitting>() {
            @Override
            public int compare(Fitting o1, Fitting o2) {
                return o1.getFittingId().compareTo(o2.getFittingId());
            }
        });
        return JSONResult.success(ipage);
    }


    @PostMapping("/addNewFitting")
    public JSONResult addNewFitting(@RequestBody Fitting fitting) {
        fitting.setCarNumber("未使用");
        fitting.setIsUsed(false);
        boolean success = fittingService.save(fitting);
        if (success) {
            return JSONResult.success("入库成功");
        }
        return JSONResult.failMsg("入库失败");
    }

    @GetMapping("/deleteFittingById")
    public JSONResult deleteFittingById(String id) {
        Fitting f = new Fitting();
        f.setFittingId(id);
        boolean success = fittingService.removeById(f);
        if (success) return JSONResult.success("删除成功");
        return JSONResult.failMsg("删除失败");
    }

    @PostMapping("/saveUsedFitting/{carNumber}")
    public JSONResult saveUsedFitting(@PathVariable String carNumber,@RequestBody List<String> l) {
      List<Fitting> list =fittingService.listByIds(l);
      if(list.size()==l.size()){
          for(Fitting f:list){
              f.setIsUsed(true);
              f.setCarNumber(carNumber);
          }
          fittingService.updateBatchById(list);
          return JSONResult.success("出库成功");
      }
      return JSONResult.failMsg("出库失败");
    }
}
