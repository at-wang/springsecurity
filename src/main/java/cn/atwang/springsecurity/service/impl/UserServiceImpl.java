package cn.atwang.springsecurity.service.impl;

import cn.atwang.springsecurity.dao.UserDao;
import cn.atwang.springsecurity.domain.User;
import cn.atwang.springsecurity.service.UserService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
