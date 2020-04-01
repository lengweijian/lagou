package com.mybatis.core;

import java.io.InputStream;

public class Resources {

    public static InputStream getResourceStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }

}
