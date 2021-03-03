package com.woniu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhouli
 * @since 2021-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_permission")
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        @ApiModelProperty(value = "菜单项")
        private String element;

        @ApiModelProperty(value = "点击某个菜单项跳转的路径")
        private String url;

        @ApiModelProperty(value = "菜单等级")
        private Integer level;

        @ApiModelProperty(value = "父级菜单ID")
        private Integer pid;

         //告诉实体类数据库表没有这个字段，查询不会出错
        @TableField(exist = false)
        private Set<Permission> children;


}
