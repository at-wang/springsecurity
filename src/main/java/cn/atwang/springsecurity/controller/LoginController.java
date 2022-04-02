package cn.atwang.springsecurity.controller;

import cn.atwang.springsecurity.domain.User;
import cn.atwang.springsecurity.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public String login(@RequestBody User user) throws Exception {
        //登录
        String login = loginService.login(user);
        return login;
    }

    @RequestMapping("/user/exit")
    public String loginExit(){
        String s = loginService.loginExit();
        return s;
    }
}
