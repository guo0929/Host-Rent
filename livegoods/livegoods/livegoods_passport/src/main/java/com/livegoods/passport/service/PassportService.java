package com.livegoods.passport.service;

import com.livegoods.commons.pojo.Result;

public interface PassportService {
    Result<Object> sendValidateCode(String phone);
    Result<Object> login(String username, String password);
}
