package com.example.carfixsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张彬
 * @since 2022-02-11
 */
@TableName("order_info")
@ApiModel(value = "OrderInfo对象", description = "")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String orderDate;

    private Integer remainMount;

    public OrderInfo(String orderDate, Integer remainMount) {
        this.orderDate = orderDate;
        this.remainMount = remainMount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public Integer getRemainMount() {
        return remainMount;
    }

    public void setRemainMount(Integer remainMount) {
        this.remainMount = remainMount;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
            "orderDate=" + orderDate +
            ", remainMount=" + remainMount +
        "}";
    }
}
