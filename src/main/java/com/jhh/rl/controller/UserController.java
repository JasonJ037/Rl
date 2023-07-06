package com.jhh.rl.controller;

import com.jhh.rl.controller.UserRequestClass.*;
import com.jhh.rl.entity.User;
import com.jhh.rl.service.UserService;
import com.jhh.rl.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/user/login")
    public Result login(@Valid @RequestBody Login login) {
        return userService.login(login);
    }

    @GetMapping("/user/getuserinfo")
    public Result info(@RequestParam("user_id") Integer id){return userService.getuserinfo(id);}

    @PostMapping("/user/updatepwd")
    public Result updatepwd(@RequestBody Updatepwd updatepwd){return userService.updatepwd(updatepwd);}

    @PostMapping("/user/setemail")
    public Result setemail(@RequestBody Email email){return userService.setemail(email);}

    @PostMapping("/user/sendfeedback")
    public Result sendfeedback(@RequestBody FeedBack feedBack){return userService.sendfeedback(feedBack);}

    @PostMapping("/back/user/adduser")
    public Result adduser(@RequestBody AddUser addUser){return userService.adduser(addUser);}

    @PostMapping("/back/user/getuserlist")
    public Result<List<User>> pagelist(@RequestBody GetUserList getUserList)
    {
        return userService.pagelist(getUserList);
    }

    @PostMapping("/back/user/updateuserinfo")
    private Result updateuserinfo(@RequestBody UpdateUserInfo updateUserInfo)
    {
        return userService.updateuserinfo(updateUserInfo);
    }

    @GetMapping("/back/user/deluser")
    public Result deluser(@RequestParam("user_id") Integer id){return userService.deluser(id);}
}
