package com.mybatis.core;


public enum MethodType {

    INSERT("insert"),
    DELETE("delete"),
    UPDATE("update"),
    SELECT("select");

    private String action;

    MethodType(String action) {
        this.action = action;
    }



    public String getAction() {
        return action;
    }





}
