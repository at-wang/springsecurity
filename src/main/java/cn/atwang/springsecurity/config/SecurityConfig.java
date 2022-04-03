package cn.atwang.springsecurity.config;

import cn.atwang.springsecurity.filter.JwtTokenFilter;
import cn.atwang.springsecurity.handler.AWSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//springSecurity的配置信息
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启鉴权配置功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AWSuccessHandler awSuccessHandler;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义认证成功调用
        http.formLogin().successHandler(awSuccessHandler);

        http
                //关闭csrf
                .csrf().disable()
                //前后端分离项目不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于登录接口允许访问
                .antMatchers("/user/login").anonymous()//匿名
                //除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();//登录后任何用户都可以访问

        //把某个过滤器添加到某个过滤器前
        http.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
        http.exceptionHandling()
                //把自定义的认证异常处理器配置到security
                .authenticationEntryPoint(authenticationEntryPoint)
                //把自定义的授权异常处理器配置到security
                .accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
    }

    //创建BCryptPasswordEncoder注入容器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
