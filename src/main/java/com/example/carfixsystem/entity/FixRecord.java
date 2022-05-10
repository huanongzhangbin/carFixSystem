package com.example.carfixsystem.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author 张彬
 * @since 2022-01-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("fix_record")
@ApiModel(value = "FixRecord对象", description = "")
public class FixRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户手机号码")
    private String phone;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("车牌号")
    private String carNumber;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("技师的名字")
    private String employeeName;

    @ApiModelProperty("配件或服务列表是个字符串")
    private String fittingNames;

    @ApiModelProperty("花费总计")
    private Double total;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    private  String reserveTime;
    @ApiModelProperty("是否出库完成")
    private Boolean hasOut;


    @ApiModelProperty("订单是否完成")
    private Boolean done;

    @ApiModelProperty("是否是在网上自己预约的")
    private Boolean reserveOnline;
}
