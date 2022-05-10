package com.example.carfixsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.carfixsystem.entity.FittingType;
import com.example.carfixsystem.service.IFittingTypeService;
import com.example.carfixsystem.util.JSONResult;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张彬
 * @since 2022-01-26
 */
@Slf4j
@Controller
@RequestMapping("/fitting-type")
public class FittingTypeController {
    @Autowired
    IFittingTypeService fittingTypeService;
    @GetMapping("/getList")
    public JSONResult getList(){
        List<FittingType> list=fittingTypeService.list();
        return JSONResult.success(list);
    }
    @GetMapping("/getFittingTypeNameList")
    public JSONResult getFittingTypeNameList(){
        List<FittingType> l=fittingTypeService.list();
        List<String> list=new ArrayList<>();
        for(FittingType f:l){
            list.add(f.getFittingName());
        }
        return JSONResult.success(list);
    }


    @GetMapping("/getFittingTypeInfoById")
    public JSONResult getFittingTypeInfoById(Integer id){
        FittingType fittingType=fittingTypeService.getById(id);
       // log.info(fittingType.toString());
        if(fittingType==null){
            return  JSONResult.failMsg("获取配件种类失败");
        }
        return JSONResult.success(fittingType);
    }
    @PostMapping("/editFittingType")
    public JSONResult editFittingType(@RequestBody FittingType fittingType){
        //log.info(fittingType.toString()+"---------------------------------------");
       boolean success=  fittingTypeService.updateById(fittingType);
       if(success){
           return JSONResult.success("配件信息修改成功");
       }
       return JSONResult.failMsg("配件信息修改失败");
    }
    @PostMapping("/addType")
    public JSONResult addFittingType(@RequestBody FittingType fittingType){
        QueryWrapper<FittingType> queryWrapper=new QueryWrapper();
        queryWrapper.eq("fitting_name",fittingType.getFittingName());
        FittingType f=fittingTypeService.getOne(queryWrapper);
        if(f!=null){
            return JSONResult.custom(205,"msg","该配件已经存在");
        }
        boolean success=fittingTypeService.save(fittingType);
        log.info("success:",success);
        if(success){
            log.info("success:",success);
            return JSONResult.success("配件添加成功");
        }
        return JSONResult.failMsg("配件添加失败");
    }
    @GetMapping("/deleteType")
    public JSONResult deleteType(Integer id){
        log.info("###############################id:",id);
        boolean success=fittingTypeService.removeById(id);
        if(success){
            return JSONResult.success("删除配件种类成功");
        }
        return JSONResult.failMsg("删除配件种类失败");
    }
}
