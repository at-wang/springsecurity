package cn.atwang.springsecurity.service;

import cn.atwang.springsecurity.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;


public interface LoginService extends IService<User> {

    public String login(User user) throws Exception;

    public String loginExit();
}
