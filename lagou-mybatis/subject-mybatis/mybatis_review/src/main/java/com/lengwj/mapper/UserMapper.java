package com.lengwj.mapper;

import com.lengwj.pojo.User;

import java.util.List;

/**
 * TODO
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-29 08:13
 */
public interface UserMapper {
    List<User> findAll();
    User findOrdersByUserId(Integer id);
}
