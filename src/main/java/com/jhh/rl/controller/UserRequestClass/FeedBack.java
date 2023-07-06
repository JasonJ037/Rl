package com.jhh.rl.controller.UserRequestClass;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FeedBack {
    @NotNull
    private String account;
    @NotNull
    private String username;
    @NotNull
    private String feedback_info;
}
