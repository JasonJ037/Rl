package com.jhh.rl.controller.UserRequestClass;

import lombok.Data;
@Data
public class UpdateUserInfo {
    private Integer user_id;
    private String password;
    private String user_status;
}
