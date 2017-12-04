import cun.common.mysql.MysqlConnect;
import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

/**
 * Created with Project: weather-service
 * Date: 2017/12/1　17:54
 * Aauthor: Casey
 * Description:
 */
public class DBTest extends TestCase {
    public void testQuery() {
        List<Map<String, String>> query = MysqlConnect.query("select * from city_weather");
        System.out.println(query);
    }
}
