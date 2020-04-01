package com.lengwj.pojo;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-29 09:00
 */
@Data
public class Order {
    private Integer id;
    private Date ordertime;
    private String total;
    private User user;
}
