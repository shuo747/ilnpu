
package com.shuo747.ilnpu.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * 安全配置类.
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 1.0.0 2017年7月25日
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/img/**","/css/**","/sass/**", "/js/**", "/fonts/**", "/news","/index","/2/user/**").permitAll() // 都可以访问
                .antMatchers("/users/**").hasRole("ADMIN")   // 需要相应的角色才能访问
                .antMatchers("/admin/**").hasRole("ADMIN")   // 需要相应的角色才能访问
                .and()
                .formLogin()   //基于 Form 表单登录验证
                .loginPage("/login").failureUrl("/login-error"); // 自定义登录界面
    }

    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication()  // 认证信息存储于内存中
        //        .withUser("shuo").password("123").roles("ADMIN");

        //.passwordEncoder(new MyPasswordEncoder())。
        //这样，页面提交时候，密码以明文的方式进行匹配。
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder()).withUser("shuo").password("123").roles("ADMIN");
    }
}
