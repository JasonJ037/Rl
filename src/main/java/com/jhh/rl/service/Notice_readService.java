package com.jhh.rl.service;

import com.jhh.rl.utils.Result;

public interface Notice_readService {
    Result setRead(Integer user_id,Integer notice_id);

    Result setAllRead(Integer user_id);
}
