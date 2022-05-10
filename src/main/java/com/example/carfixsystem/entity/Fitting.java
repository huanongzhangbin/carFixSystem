package com.example.carfixsystem.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张彬
 * @since 2022-01-26
 */
@ApiModel(value = "Fitting对象", description = "")
public class Fitting implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId
    @ApiModelProperty("配件编号")
    private String fittingId;

    @ApiModelProperty("配件名")
    private String fittingName;

    @ApiModelProperty("车主的号码")
    private String carNumber;

    @ApiModelProperty("是否使用过")
    private Boolean isUsed;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;



    public String getFittingId() {
        return fittingId;
    }

    public void setFittingId(String fittingId) {
        this.fittingId = fittingId;
    }
    public String getFittingName() {
        return fittingName;
    }

    public void setFittingName(String fittingName) {
        this.fittingName = fittingName;
    }
    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Fitting{" +
                "fittingId='" + fittingId + '\'' +
                ", fittingName='" + fittingName + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", isUsed=" + isUsed +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
