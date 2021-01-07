package com.hxtx.utils;

import java.util.List;
import java.util.Map;

/**
 * MysqlTest
 *
 * @desc:
 * @author: sunweihong
 * @date: 2020/7/6 22:35
 **/
public class MysqlTest {
    public static void main(String[] args) {
        List<Map<String, Object>> maps = JdbcUtils.executeQuery("select * from sc_login_user order by USER_ID DESC LIMIT 2");
        for (Map<String, Object> map : maps) {
            System.out.println("--------------------------------------------------");
            for (String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }
    }
}
