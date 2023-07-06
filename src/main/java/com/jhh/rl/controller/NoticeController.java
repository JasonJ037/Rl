package com.jhh.rl.controller;

import com.jhh.rl.controller.DTO.getnoticelistDTO;
import com.jhh.rl.controller.NoticeRequestClass.AddNotice;
import com.jhh.rl.controller.NoticeRequestClass.GetNoticeList;
import com.jhh.rl.entity.Notice;
import com.jhh.rl.service.NoticeService;
import com.jhh.rl.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    @PostMapping("/back/notice/addnotice")
    public Result addnotice(@RequestBody AddNotice addNotice){return noticeService.addnotice(addNotice);}

    @PostMapping("/back/notice/getnoticelist")
    public Result<List<Notice>> noticelist(@RequestBody GetNoticeList getNoticeList){
        return noticeService.noticelist(getNoticeList);
    }

    @GetMapping("/back/notice/delnotice")
    public Result delnotice(@RequestParam("notice_id") Integer id){return noticeService.delnotice(id);}

    @GetMapping("/front/notice/getnoticelist")
    public Result<List<getnoticelistDTO>> getnoticelist(@RequestParam("user_id") Integer id){
        return noticeService.getnoticelist(id);
    }
}
