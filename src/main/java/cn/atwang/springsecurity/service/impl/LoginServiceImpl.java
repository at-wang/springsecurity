package cn.atwang.springsecurity.service.impl;

import cn.atwang.springsecurity.dao.UserDao;
import cn.atwang.springsecurity.domain.LoginUser;
import cn.atwang.springsecurity.domain.User;
import cn.atwang.springsecurity.service.LoginService;
import cn.atwang.springsecurity.utils.JwtUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<UserDao, User> implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;



    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String login(User user) throws Exception {
        //AuthenticationManager的方法authenticate对用户进行认证
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);//需要Authentication的接口实现类
        //认证失败给出提示
        if (authenticate == null) {
            throw new RuntimeException("登录失败");
        }
        //认证通过，使用userid生成jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id, "atwang", "", 60 * 60 * 1000);

        //把完整用户传入redis userid作为key
        stringRedisTemplate.boundValueOps(id).set(JSON.toJSONString(loginUser));
        return jwt;
    }

    @Override
    public String loginExit() {
        //获取SecurityContextHolder中的用户id（类似缓存）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        stringRedisTemplate.delete(userId);
        return "退出成功";

    }
}
