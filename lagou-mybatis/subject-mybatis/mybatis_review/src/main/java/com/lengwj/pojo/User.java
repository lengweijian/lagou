package com.lengwj.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-29 08:13
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String birthday;
    List<Order> orders;
}
