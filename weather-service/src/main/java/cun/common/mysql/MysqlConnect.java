package cun.common.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Project: weather-service
 * Date: 2017/12/1　17:41
 * Aauthor: Casey
 * Description:
 */
public class MysqlConnect {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    private static final String DB_URL = "jdbc:mysql://192.168.4.225:21406/wh_wallet";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cun?useUnicode=true&characterEncoding=UTF-8"; //47.94.166.202, 必须加上问号后这一句, 不然插入中文有问题

    //  Database credentials -- 数据库名和密码自己修改
//    private static final String USER = "wh_wallet";
    private static final String USER = "cun";
//    private static final String PASS = "jJZYOcFyKCmGQgAMvtRxWiqF3DU0PWuz";
    private static final String PASS = "091301";

    private static Connection conn = null;
    private static Statement stmt = null;

    private static void init() {
        conn = getConnection();
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("mysql JDBC 初始化失败. . . ");
        }
    }

    private static void close() {
        try {
            if (null != stmt)
                stmt.close();
            if (null != conn)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* 查询数据库，输出符合要求的记录的情况*/
    public static List<Map<String, String>> query(String sql) {
        init();
        List<Map<String, String>> records = new ArrayList<Map<String, String>>();
        ResultSet rs = null;

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData schema = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> record = new HashMap<String, String>();
                for (int j = 1; j < schema.getColumnCount() + 1; j++) {
                    record.put(schema.getColumnName(j), rs.getString(schema.getColumnName(j)));
                }
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
        return records;
    }


    public static boolean execute(String sql) {
        init();
        boolean flag = false;
        try {
            Statement st = conn.createStatement();
            flag = st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return flag;
    }

    // 批量执行sql语句
    public static void execute(String... sqls) {
        for (String sql : sqls) {
            execute(sql);
        }
    }

    /* 获取数据库连接的函数*/
    private static Connection getConnection() {
        Connection con = null;    //创建用于连接数据库的Connection对象
        try {
            Class.forName(JDBC_DRIVER);// 加载Mysql数据驱动
            con = DriverManager.getConnection(DB_URL, USER, PASS);// 创建数据连接
        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con;    //返回所建立的数据库连接
    }


}
