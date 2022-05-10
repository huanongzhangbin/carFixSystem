package com.example.carfixsystem.service.impl;

import com.example.carfixsystem.entity.OrderInfo;
import com.example.carfixsystem.mapper.OrderInfoMapper;
import com.example.carfixsystem.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张彬
 * @since 2022-02-11
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {
    @Resource
    OrderInfoMapper orderInfoMapper;
    @Override
    public List<Integer> getRemainInfos(List<String> dateList) {
        List<Integer> remainOrderInfos=new ArrayList<>();
        for(String d:dateList){
           OrderInfo o= orderInfoMapper.selectById(d);
            if(o!=null){
                remainOrderInfos.add(o.getRemainMount());
            }else{
                OrderInfo no=new OrderInfo(d,60);
                orderInfoMapper.insert(no);
                remainOrderInfos.add(no.getRemainMount());
            }
        }
        return remainOrderInfos;
    }

    @Override
    public boolean decreaseRemain(String id) {
        OrderInfo orderInfo=orderInfoMapper.selectById(id);
        int remain=orderInfo.getRemainMount();
        if(remain>0){
            remain--;
            orderInfo.setRemainMount(remain);
            return orderInfoMapper.updateById(orderInfo)>0;
        }
        return false;
    }
}
