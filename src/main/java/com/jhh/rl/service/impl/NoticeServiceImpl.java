package com.jhh.rl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhh.rl.controller.DTO.getnoticelistDTO;
import com.jhh.rl.controller.NoticeRequestClass.AddNotice;
import com.jhh.rl.controller.NoticeRequestClass.GetNoticeList;
import com.jhh.rl.entity.Notice;
import com.jhh.rl.entity.Notice_read;
import com.jhh.rl.entity.User;
import com.jhh.rl.mapper.NoticeMapper;
import com.jhh.rl.mapper.Notice_readMapper;
import com.jhh.rl.mapper.UserMapper;
import com.jhh.rl.service.NoticeService;
import com.jhh.rl.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.management.openmbean.InvalidOpenTypeException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private  UserMapper userMapper;

    @Resource
    private Notice_readMapper notice_readMapper;
    @Override
    public Result addnotice(AddNotice addNotice) {
        if(addNotice.getNotice_detail().isEmpty()||addNotice.getNotice_title().isEmpty())
        {
            return Result.fail("发布失败");
        }

        Notice notice = new Notice();
        notice.setNoticeDetail(addNotice.getNotice_detail());
        notice.setNoticeTitle(addNotice.getNotice_title());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        notice.setCreateTime(sdf.format(new Date()));

        int i = noticeMapper.insert(notice);
        if(i==0) return Result.fail("发布失败");
        return Result.ok("发布成功");
    }

    @Override
    public Result<List<Notice>> noticelist(GetNoticeList getNoticeList) {
        IPage<Notice> ipage=new Page<>(getNoticeList.getPage_no(),getNoticeList.getPage_size());
        QueryWrapper<Notice>queryWrapper=new QueryWrapper<>();

        queryWrapper.like("notice_title",getNoticeList.getNotice_title());

        ipage=noticeMapper.selectPage(ipage,queryWrapper);
        List<Notice>list=ipage.getRecords();

        if(list.isEmpty())
            return Result.fail("查询通知列表失败");
        return Result.ok("查询通知列表成功",list, list.size());
    }

    @Override
    public Result delnotice(Integer id) {
        if(id==0) return Result.fail("无效输入");
        Notice notice=noticeMapper.selectById(id);
        if(notice==null) return Result.fail("通知不存在!");

        int i = noticeMapper.deleteById(id);
        if(i==0) return Result.fail("通知删除失败");
        return Result.ok("通知已被删除!");

    }

    @Override
    public Result<List<getnoticelistDTO>> getnoticelist(Integer id) {
        if(id==0) return Result.fail("无效输入");

        User user=userMapper.selectById(id);
        if(user==null) return Result.fail("查询通知列表失败");

        List<getnoticelistDTO> getnoticelistDTOS=new ArrayList<>();

        //先把通知状态为正常的查找出来
        QueryWrapper<Notice>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete","正常");
        List<Notice> notices = noticeMapper.selectList(queryWrapper);
        System.out.println(notices);

        //在Notice_read中查找user_id
        QueryWrapper<Notice_read>queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("user_id",id);
        List<Notice_read> notice_reads = notice_readMapper.selectList(queryWrapper1);
        System.out.println(notice_reads);

        for(Notice notice:notices){
            for(Notice_read notice_read:notice_reads){
                if(notice.getId()==notice_read.getNoticeId())
                {
                    getnoticelistDTO noticelist=new getnoticelistDTO();
                    noticelist.setNotice_id(notice.getId());
                    noticelist.setNotice_title(notice.getNoticeTitle());
                    noticelist.setNotice_detail(notice.getNoticeDetail());
                    noticelist.setCreate_time(notice.getCreateTime());
                    noticelist.setUser_id(id);
                    noticelist.setRead_notice_id(notice_read.getNoticeId());
                    noticelist.setIs_read(notice_read.getIsRead());

                    getnoticelistDTOS.add(noticelist);
                }
            }
        }

        System.out.println(getnoticelistDTOS);
        return Result.ok("查询通知列表成功",getnoticelistDTOS);
    }
}
