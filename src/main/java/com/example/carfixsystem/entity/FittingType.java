package com.example.carfixsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-01-26
 */
@TableName("fitting_type")
@ApiModel(value = "FittingType对象", description = "")
public class FittingType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("配件名")
    private String fittingName;

    @ApiModelProperty("价格")
    private Double price;

    @ApiModelProperty("是否需要保存到维修fix_record表中")
    private Boolean isSave;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getFittingName() {
        return fittingName;
    }

    public void setFittingName(String fittingName) {
        this.fittingName = fittingName;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Boolean getIsSave() {
        return isSave;
    }

    public void setIsSave(Boolean isSave) {
        this.isSave = isSave;
    }

    @Override
    public String toString() {
        return "FittingType{" +
            "id=" + id +
            ", fittingName=" + fittingName +
            ", price=" + price +
            ", isSave=" + isSave +
        "}";
    }
}
