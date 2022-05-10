package com.example.carfixsystem.controller;


import com.example.carfixsystem.entity.OrderInfo;
import com.example.carfixsystem.service.IOrderInfoService;
import com.example.carfixsystem.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张彬
 * @since 2022-02-11
 */
@RestController
@RequestMapping("/order-info")
@Slf4j
public class OrderInfoController {
    @Autowired
    IOrderInfoService orderInfoService;

    @ApiOperation("得到预约剩余量")
    @PostMapping("/getRemainOrderInfos")
    public JSONResult getRemainOrderInfos(@RequestBody List<String> dateList){
        log.info(dateList.toString());
        List<Integer> remainOrderInfos=orderInfoService.getRemainInfos(dateList);
         return   JSONResult.success(remainOrderInfos);

    }

}
