package com.jhh.rl.controller;

import com.jhh.rl.mapper.Notice_readMapper;
import com.jhh.rl.service.Notice_readService;
import com.jhh.rl.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Notice_readController {

    @Resource
    private Notice_readService notice_readService;

    @GetMapping("/front/notice/set_read")
    public Result setRead(@RequestParam("user_id") Integer user_id,@RequestParam("notice_id")Integer notice_id)
    {
        return notice_readService.setRead(user_id,notice_id);
    }

    @GetMapping("/front/notice/all_set_read")
    public Result allsetread(@RequestParam("user_id") Integer id){
        return notice_readService.setAllRead(id);
    }
}
