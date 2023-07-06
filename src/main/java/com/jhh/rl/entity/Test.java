package com.jhh.rl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Test implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String createName;

    private String testName;

    private String testStatus;

    private String createTime;

    private String updateTime;
}
