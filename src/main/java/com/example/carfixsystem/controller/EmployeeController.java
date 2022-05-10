package com.example.carfixsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.carfixsystem.entity.Employee;
import com.example.carfixsystem.service.IEmployeeService;
import com.example.carfixsystem.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张彬
 * @since 2022-01-26
 */
@Slf4j
@Api("employee控制器")
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    IEmployeeService iEmployeeService;

    @ApiOperation("获取雇员列表")
    @GetMapping("/getList")
    public JSONResult getList(@RequestParam(value = "pagenum") int current, @RequestParam(value = "pagesize") int size){
        Page<Employee> employeePage=new Page<>(current,size);
         IPage<Employee> ipage= iEmployeeService.page(employeePage);//这里可以写查询条件wrapper

        return JSONResult.success(ipage);
    }
    @PostMapping("/addEmployee")
    public JSONResult addEmloyee(@RequestBody Employee e){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("employee_name",e.getEmployeeName());
        e.setBusy(false);
       Employee employee= iEmployeeService.getOne(queryWrapper);
       if (employee!=null){
           return JSONResult.custom(201,"msg","该员工已存在");
       }
        iEmployeeService.save(e);
       return  JSONResult.success("添加成功");
    }
    @GetMapping("/getEmployeeById")
    public JSONResult<Employee> getEmployeeById(Integer id){
        Employee e= iEmployeeService.getById(id);
        if(e!=null){
            return JSONResult.success(e);
        }
        return JSONResult.failMsg("获取用户列表失败");

    }
    @GetMapping("/deleteEmployeeById")
    public JSONResult deleteEmployeeById(Integer id){
     boolean isDelete=   iEmployeeService.removeById(id);
        if(isDelete){
            return JSONResult.success("删除成功");
        }else{
            return  JSONResult.failMsg("删除失败");
        }
    }
    @GetMapping("/editEmployeeRole")
    public JSONResult editEmployeeRole(@RequestParam Integer id,@RequestParam String roleName){
        log.info("id:",id);
        log.info("role:",roleName);
        log.info("-------------------------------------------");
        Employee e= iEmployeeService.getById(id);
        log.info(e.toString());
        if(e!=null){
            log.info("aaaaaaaaaa@@@@@Q@@@@@@@@@");
            e.setRoleName(roleName);
            boolean  success =iEmployeeService.updateById(e);
            if(success) return JSONResult.success("角色更新成功");
        }
        return JSONResult.failMsg("角色更新失败");
    }
    @GetMapping("/getFixEmployeeNameList")
    public JSONResult getFixEmployeeNameList(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("role_name","修理工");
        queryWrapper.eq("busy",false);
         List<Employee> l=  iEmployeeService.list(queryWrapper);
         List<String> list=new ArrayList<>();
        for (Employee e:l) {
            list.add(e.getEmployeeName());
        }
        return JSONResult.success(list);
    }
}
