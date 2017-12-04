package cun;

import cun.common.mysql.MysqlConnect;

import java.util.List;
import java.util.Map;

/**
 * Created with Project: weather-service
 * Date: 2017/12/1　18:31
 * Aauthor: Casey
 * Description:
 */
public class GG {
    public static void main(String[] args) {
        List<Map<String, String>> query = MysqlConnect.query("select * from city_weather");
        System.out.println(query);
        System.out.println(111);
    }
}
