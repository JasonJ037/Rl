package com.jhh.rl.controller.UserRequestClass;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddUser {
    @NotNull
    private String account;
    @NotNull
    private String adduserpwd1;
    @NotNull
    private String password;
    @NotNull
    private String user_status;
    @NotNull
    private String username;
}
