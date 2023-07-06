package com.jhh.rl.controller.UserRequestClass;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Login {
    /**
     * 登录账号
     */
    @NotNull
    private String account;
    /**
     * 密码
     */
    @NotNull
    private String password;
}
