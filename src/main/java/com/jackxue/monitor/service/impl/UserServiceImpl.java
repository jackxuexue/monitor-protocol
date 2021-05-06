package com.jackxue.monitor.service.impl;

import com.jackxue.monitor.config.DataSource;
import com.jackxue.monitor.config.DataSourceNames;
import com.jackxue.monitor.entities.User;
import com.jackxue.monitor.mapper.UserMapper;
import com.jackxue.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @DataSource(value = DataSourceNames.ONE)
    @Override
    public List<User> list1() {
        return userMapper.list();
    }

    @DataSource(value = DataSourceNames.TWO)
    @Override
    public List<User> list2() {
        return userMapper.list();
    }
}
