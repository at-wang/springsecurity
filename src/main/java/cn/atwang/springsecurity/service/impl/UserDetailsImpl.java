package cn.atwang.springsecurity.service.impl;

import cn.atwang.springsecurity.dao.UserDao;
import cn.atwang.springsecurity.domain.LoginUser;
import cn.atwang.springsecurity.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
        lqw.eq(User::getUserName,username);
        User user = userDao.selectOne(lqw);
        System.out.println(user);
        //如果没有用户抛出异常
        if (user==null){
            throw new RuntimeException("用户名或密码错误");
        }

        //TODO 查询相应的权限信息
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("test", "admin"));


        //把数据封装成UserDetails类 =>新建一个登录类实现userDetail
        return new LoginUser(user,arrayList);
    }
}
