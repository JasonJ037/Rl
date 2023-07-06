package com.jhh.rl.service;

import com.jhh.rl.controller.DTO.loginDTO;
import com.jhh.rl.controller.UserRequestClass.*;
import com.jhh.rl.entity.User;
import com.jhh.rl.utils.Result;

import java.util.List;

public interface UserService {

    Result<loginDTO> login(Login login);

    Result<User> getuserinfo(Integer id);

    Result updatepwd(Updatepwd updatepwd);

    Result setemail(Email email);

    Result sendfeedback(FeedBack feedBack);

    Result adduser(AddUser addUser);

    Result<List<User>> pagelist(GetUserList getUserList);

    Result updateuserinfo(UpdateUserInfo updateUserInfo);

    Result deluser(Integer id);


}
