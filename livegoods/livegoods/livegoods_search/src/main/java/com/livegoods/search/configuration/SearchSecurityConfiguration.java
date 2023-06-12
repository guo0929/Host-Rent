package com.livegoods.search.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 正式商业开发中，初始化逻辑，一定在后台管理平台中。前台客户平台中，不能定义。
 * 任何的保护都是没意义的。
 */
@Configuration
public class SearchSecurityConfiguration extends WebSecurityConfigurerAdapter {
    // 配置Security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自定义配置
        http.authorizeRequests()
                .antMatchers("/init").authenticated()
                .antMatchers("/**").permitAll();

        // 当前环境中没有自定义的任何Security逻辑，就是通过Security做一下登录认证。
        // 所以需要使用默认配置，增加特定配置。
        // 默认配置，也就是super.configure(http)，必须最后调用。
        super.configure(http);
    }
}
