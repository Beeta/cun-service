package cun;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created with Project: httpdemo
 * Date: 2017/12/1　11:31
 * Aauthor: Casey
 * Description: 天气服务
 */
public class WeathService {
    public static <T> T getData(String url, Class<T> clazz) {
//        JSON.parseObject("",clazz);

        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        HttpGet httpGet2 = new HttpGet(url);
        httpGet2.setConfig(requestConfig);

        String srtResult = "";
        try {
            HttpResponse httpResponse = httpCilent2.execute(httpGet2);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                srtResult = EntityUtils.toString(httpResponse.getEntity(),"utf-8");//获得返回的结果
                T t = JSON.parseObject(srtResult, clazz);
                return t;
//                System.out.println(t.getWeatherinfo().getCity());

            }else if(httpResponse.getStatusLine().getStatusCode() == 400){
                //..........
            }else if(httpResponse.getStatusLine().getStatusCode() == 500){
                //.............
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpCilent2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
