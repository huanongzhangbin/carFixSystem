package com.example.carfixsystem.controller;


import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张彬
 * @since 2022-01-26
 */
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
@Api("user控制器")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IEmployeeService iEmployeeService;

    @GetMapping("/getEmployeeById")
    public JSONResult<Employee> getEmployeeById(Integer id) {
        Employee e = iEmployeeService.getById(id);
        if (e != null) {
            return JSONResult.success(e);
        }
        return JSONResult.failMsg("获取用户列表失败");

    }
}
