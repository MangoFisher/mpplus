package com.mango.mpplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    //实体类中驼峰命名的变量，同数据中带下划线的列一一对应（即create_time）
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //乐观锁的版本号
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    //逻辑删除标记字段
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
