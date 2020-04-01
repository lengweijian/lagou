package com.mybatis.core;

import com.utils.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析之后的sql
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-28 14:00
 */
@Data
@AllArgsConstructor
public class BoundSql {

    private String parsedSql;

    private List<ParameterMapping> params = new ArrayList<>();
}
