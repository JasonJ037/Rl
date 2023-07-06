package com.jhh.rl.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhh.rl.controller.DTO.loginDTO;
import com.jhh.rl.controller.UserRequestClass.*;
import com.jhh.rl.entity.User;
import com.jhh.rl.mapper.UserMapper;
import com.jhh.rl.service.UserService;
import com.jhh.rl.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private HttpSession httpSession;


    @Override
    public Result<loginDTO> login(Login login) {
        User user=queryByUserAccount(login.getAccount());
        System.out.println(user);
        //账号不存在
        if (null == user) {
            return Result.fail("登录失败, 账号或密码错误");
        }
        //密码错误
        if(!login.getPassword().equals(user.getPassword())){
            return Result.fail("登录失败, 账号或密码错误");
        }
        httpSession.setAttribute("user",user);

        loginDTO loginDTO=new loginDTO();
        loginDTO.setUser_id(user.getId());
        loginDTO.setUsername(user.getUsername());
        loginDTO.setIdentity(user.getIdentity());
        loginDTO.setAccount(user.getAccount());
        loginDTO.setPassword("");
        loginDTO.setUser_status(user.getUserStatus());
        loginDTO.setCreate_time(user.getCreateTime());
        loginDTO.setLogin_time(user.getLoginTime());

        return Result.ok("登录成功",loginDTO);

    }

    @Override
    public Result<User> getuserinfo(Integer id) {
        if(id==0) return Result.fail("无效输入");
        User user=userMapper.selectById(id);
        if(user==null)
        {
            return Result.fail("用户id无效");
        }
        User user1=user;
        user1.setPassword("");

        return Result.ok("获取用户信息成功",user1);
    }

    @Override
    public Result updatepwd(Updatepwd updatepwd) {
        User user=userMapper.selectById(updatepwd.getUser_id());
        if(user==null)
        {
            return Result.fail("用户不存在");
        }

        if(!updatepwd.getOldpwd().equals(user.getPassword()))
        {
            return Result.fail("旧密码错误");
        }else if(updatepwd.getOldpwd().equals(updatepwd.getNewpwd())){
            return Result.fail("新密码不能与旧密码相同");
        }

        user.setPassword(updatepwd.getNewpwd());
        userMapper.updateById(user);

        return Result.ok("密码修改成功, 请重新登录");
    }

    @Override
    public Result setemail(Email email) {
        User user=userMapper.selectById(email.getUser_id());
        if(user==null)
        {
            return Result.fail("用户不存在");
        }

        user.setEmail(email.getEmail());
        userMapper.updateById(user);

        return Result.ok("保存成功");

    }

    @Override
    public Result sendfeedback(FeedBack feedBack) {

        return null;
    }

    @Override
    public Result adduser(AddUser addUser) {
        User user=queryByUserAccount(addUser.getAccount());
        if(null!=user)
            {
            return Result.fail("创建用户信息失败");
        }

        if(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(addUser.getPassword())
                && StringUtils.isNotBlank(addUser.getAdduserpwd1())){
            if(!addUser.getPassword().equals(addUser.getAdduserpwd1()))
        {
            return Result.fail("创建用户信息失败");
        }
        }

        User user1=new User();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        user1.setCreateTime(sdf.format(new Date()));
        user1.setAccount(addUser.getAccount());
        user1.setUsername(addUser.getUsername());
        user1.setPassword(addUser.getPassword());
        user1.setUserStatus(addUser.getUser_status());

        int i =userMapper.insert(user1);
        if(i==0){
            return Result.fail("创建用户信息失败");
        }
        return Result.ok("创建用户信息成功");
    }

    @Override
    public Result<List<User>> pagelist(GetUserList getUserList) {
        IPage<User>ipage=new Page<>(getUserList.getPage_no(),getUserList.getPage_size());
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();

        String account=getUserList.getAccount();
        String username=getUserList.getUsername();
        String user_status= getUserList.getUser_status();

        if(Objects.equals("全部", user_status)||user_status.isEmpty())
        {
            queryWrapper.like("username",username)
                    .like("account",account);
        }else
        {
            queryWrapper.like("username",username)
                    .like("account",account)
                    .eq("user_status",user_status);
        }

        queryWrapper.orderByDesc("id");

        ipage=userMapper.selectPage(ipage,queryWrapper);
        List<User>list=ipage.getRecords();

        if(list.isEmpty()) return Result.fail("查询用户列表失败");

        return Result.ok("查询用户列表成功",list, list.size());

    }

    @Override
    public Result updateuserinfo(UpdateUserInfo updateUserInfo) {
        User user=userMapper.selectById(updateUserInfo.getUser_id());
        if(user==null) return Result.fail("用户不存在");

        if(!updateUserInfo.getUser_status().equals("正常")&&!updateUserInfo.getUser_status().equals("封禁"))
        {
            return Result.fail("用户信息修改失败");
        }

        user.setUserStatus(updateUserInfo.getUser_status());

        if(!updateUserInfo.getPassword().isEmpty()) {
            user.setPassword(updateUserInfo.getPassword());
        }
        userMapper.updateById(user);
        return Result.ok("用户信息修改成功");
    }

    @Override
    public Result deluser(Integer id) {
        if(id==0) return Result.fail("无效输入");
        User user=userMapper.selectById(id);
        if(user==null) return Result.fail("用户不存在");

        user.setUserStatus("封禁");
        int i = userMapper.updateById(user);
        if(i==0) return Result.fail("用户封禁失败");
        return Result.ok("用户已被封禁");
    }

    private User queryByUserAccount(String account) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }


}
