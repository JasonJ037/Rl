package com.jhh.rl.controller.UserRequestClass;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Updatepwd {
    @NotNull
    private String newpwd;
    @NotNull
    private String oldpwd;
    @NotNull
    private Integer user_id;
}
