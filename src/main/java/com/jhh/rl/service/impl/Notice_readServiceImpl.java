package com.jhh.rl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jhh.rl.entity.Notice_read;
import com.jhh.rl.mapper.Notice_readMapper;
import com.jhh.rl.service.Notice_readService;
import com.jhh.rl.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Notice_readServiceImpl implements Notice_readService {
    @Resource
    private Notice_readMapper notice_readMapper;
    @Override
    public Result setRead(Integer user_id, Integer notice_id) {
        if(user_id==0||notice_id==0) return Result.fail("输入无效");

        QueryWrapper<Notice_read>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user_id)
                .eq("notice_id",notice_id);

        Notice_read notice_read = notice_readMapper.selectOne(queryWrapper);

        notice_read.setIsRead("已读");

        int i = notice_readMapper.updateById(notice_read);
        if(i==0) return Result.fail("设置失败");

        return Result.ok("已读");
    }

    @Override
    public Result setAllRead(Integer user_id) {
        if(user_id==0) return Result.fail("输入无效");
        QueryWrapper<Notice_read>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user_id);

        List<Notice_read> notice_reads = notice_readMapper.selectList(queryWrapper);

        for (Notice_read notice_read:notice_reads){
            notice_read.setIsRead("已读");
            int i = notice_readMapper.updateById(notice_read);
            if(i==0) return Result.fail("设置失败");
        }

        return Result.ok("全部已读");

    }
}
