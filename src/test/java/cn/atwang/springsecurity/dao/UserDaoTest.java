package cn.atwang.springsecurity.dao;


import cn.atwang.springsecurity.domain.LoginUser;
import cn.atwang.springsecurity.domain.User;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserDao userDao;

    @Test
    public void testBCryptPasswordEncoder(){
        BCryptPasswordEncoder b=new BCryptPasswordEncoder();
        final String encode = b.encode("1234");
        final String encode2 = b.encode("1234");
        System.out.println("加密1次-----"+encode);
        System.out.println("加密2次-----"+encode2);
    }
    @Test
    public void testUser(){
        List<User> users = userDao.selectList(null);
        System.out.println(users);
    }

    @Test
    public void test(){
        String s = stringRedisTemplate.boundValueOps("1").get();
        System.out.println(s);
        LoginUser loginUser = JSON.parseObject(s, LoginUser.class);
        System.out.println(loginUser);
    }
    @Test
    public void test2(){
        User user = userDao.selectById(1);
        System.out.println(user);
        System.out.println("-------------------");
        User user2 = userDao.selectById(1);
        System.out.println(user2);
    }


}