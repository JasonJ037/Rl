package com.jhh.rl.controller.UserRequestClass;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetUserList {
    @NotNull
    private Integer page_no;
    @NotNull
    private Integer page_size;
    @NotNull
    private String account;
    @NotNull
    private String username;
    @NotNull
    private String user_status;
}
