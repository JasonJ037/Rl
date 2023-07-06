package com.jhh.rl.controller.NoticeRequestClass;

import lombok.Data;

@Data
public class GetNoticeList {
    private Integer page_no;
    private Integer page_size;
    private String notice_title;
}
