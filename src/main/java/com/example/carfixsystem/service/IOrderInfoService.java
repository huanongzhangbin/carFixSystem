package com.example.carfixsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.carfixsystem.entity.OrderInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张彬
 * @since 2022-02-11
 */
public interface IOrderInfoService extends IService<OrderInfo> {
  public List<Integer> getRemainInfos(List<String> dateList);
  public boolean decreaseRemain(String id);
}
