package cun;

import cun.common.LogSupport;
import cun.common.mysql.MysqlConnect;
import cun.pojo.XinZhiData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created with Project: httpdemo
 * Date: 2017/12/1　11:53
 * Aauthor: Casey
 * Description: 输出
 */
public class CityWeatherOutPut extends LogSupport {
    private String city;
    private String lastUpdateTime = "";

    public CityWeatherOutPut(String city) {
        this.city = city;
    }

    public void outPut() {
        log.info("获取 " + city + "的天气信息 . . . ");
        XinZhiData data = WeathService.getData("https://api.seniverse.com/v3/weather/now.json?key=g1smswu8xvqtwwgl&location=" + city + "&language=zh-Hans&unit=c", XinZhiData.class);

        if (data == null) {
            log.error("获取 " + city + "的天气信息数据为空 ");
            return;
        }
        List<XinZhiData.ResultsBean> results = data.getResults();
        if (results != null && results.size() > 0) {
            String cityName = results.get(0).getLocation().getName();
            String text = results.get(0).getNow().getText();
            String code = results.get(0).getNow().getCode();
            String temp = results.get(0).getNow().getTemperature();// 温度
            String updateTime = results.get(0).getLast_update();
            String update_date = updateTime.substring(0, 10);
            String update_time = updateTime.substring(11, 19);
            String str = String.format("%s,%s,%s,%s,%s,%s", cityName, text, code, temp, update_date, update_time);
            String str4sql = String.format("'%s','%s',%s,%s,'%s'", cityName, text, code, temp, update_date + " " + update_time);

            if (!updateTime.equals(lastUpdateTime)) {
//                写入
                Path path = Paths.get("./" + city + ".csv");
                try {
                    if (!Files.exists(path)) {
                        Files.createDirectories(path.getParent());
                        Files.createFile(path);
                        Files.write(path, ("城市名,天气,代码,温度,更新时间" + "\n").getBytes());
                    }
                    Files.write(path, (str + "\n").getBytes(), StandardOpenOption.APPEND);
                    insertDB(str4sql);
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            } else
                log.info(city + " 尚未更新. . . " + lastUpdateTime);
            lastUpdateTime = updateTime;
        }
    }

    private void insertDB(String str) {
        String sql = "insert into city_weather(city,w_text,w_code,temp,update_time)\n" +
                "values(" + str + ")";
        MysqlConnect.execute(sql);
    }

}
