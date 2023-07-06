package com.jhh.rl.service;

import com.jhh.rl.controller.DTO.getnoticelistDTO;
import com.jhh.rl.controller.NoticeRequestClass.AddNotice;
import com.jhh.rl.controller.NoticeRequestClass.GetNoticeList;
import com.jhh.rl.entity.Notice;
import com.jhh.rl.utils.Result;

import java.util.List;

public interface NoticeService {
    Result addnotice(AddNotice addNotice);

    Result<List<Notice>> noticelist(GetNoticeList getNoticeList);

    Result delnotice(Integer id);

    Result<List<getnoticelistDTO>> getnoticelist(Integer id);
}
