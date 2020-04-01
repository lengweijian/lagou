package com.lengwj.mapper;

import com.lengwj.pojo.Order;
import com.lengwj.pojo.User;

import java.util.List;

/**
 * TODO
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-29 09:02
 */
public interface OrderMapper {
    User findOrdersByUserId(Integer userId);
}
