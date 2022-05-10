package com.example.carfixsystem.service.impl;

import com.example.carfixsystem.entity.User;
import com.example.carfixsystem.mapper.UserMapper;
import com.example.carfixsystem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张彬
 * @since 2022-01-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
