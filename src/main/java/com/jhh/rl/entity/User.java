package com.jhh.rl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonProperty("user_id")
    private Integer id;

    private String username;

    private String account;

    private String password;

    private String identity;

    @JsonProperty("user_status")
    private String userStatus;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("login_time")
    private String loginTime;

    private String email;


}
