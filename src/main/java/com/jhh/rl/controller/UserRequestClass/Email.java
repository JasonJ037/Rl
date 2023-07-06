package com.jhh.rl.controller.UserRequestClass;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Email {
    /**
     * 用户邮箱
     */
    @NotNull
    private String email;
    /**
     * 用户id
     */
    @NotNull
    private Integer user_id;
}
