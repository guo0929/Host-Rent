package com.livegoods.passport.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.passport.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PassportController {
    @Autowired
    private PassportService passportService;

    /**
     * 登录逻辑。
     *  实现逻辑
     *   1、 根据username，拼接redis的key，访问redis查询现有验证码
     *   2、 校验验证码和客户端发送的password是否一致。
     *   3、 如果不同，登录失败
     *   4、 如果相同，登录成功
     *   5、 登录成功后，记录登录日志，包括数据：
     *       {"手机号","登录时间", "登录方式[手机号验证码|用户名密码]"}
     *   6、 可选操作，如果用户是第一次登录，可以考虑新增一条用户注册数据。注意，不是登录日志。
     *       数据包括： {"手机号", "注册时间", "最近登录时间，如果存在此数据，每次登录都要更新"}
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result<Object> login(String username, String password){
        return passportService.login(username, password);
    }

    /**
     * 发送验证码。正规实现是使用SMS服务，发送短信。
     * 当前系统使用输出打印的方式实现。
     *
     * 验证码使用逻辑：
     *  每个验证码只能使用唯一一次。无论是否正确，使用后立刻删除。
     *  重复申请验证码：
     *   删除已有验证码，生成新的验证码，每次发送的验证码给一个标记符号。
     *
     * @param phone
     * @return
     *  { "status":200, "msg":"成功结果消息" }
     *  { "status":500, "msg":"失败结果消息" }
     */
    @PostMapping("/sendyzm")
    public Result<Object> sendValidateCode(String phone){
        return passportService.sendValidateCode(phone);
    }
}
