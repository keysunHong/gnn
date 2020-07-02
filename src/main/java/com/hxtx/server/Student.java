package com.hxtx.server;

import java.io.Serializable;

/**
 * Student
 *
 * @author sunweihong
 * @desc 描述
 * @date 2020/7/2 15:09
 **/
public class Student implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
