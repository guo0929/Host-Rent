package com.livegoods.passport.dao.redis;

import java.util.concurrent.TimeUnit;

// 专门访问redis的DAO。 DAO也可以使用责任链|方法调用链实现。
// 可以在livegoods_data_redis服务中，定义一个通用的DAO。在此调用，实现有业务逻辑的DAO。
public interface PassportDao4Redis {
    // 查询验证码
    String getValidateCode(String key);
    // 保存验证码
    void setValidateCode(String key, String validateCode, long times, TimeUnit unit);
    // 删除验证码
    void removeValidateCode(String key);
}
