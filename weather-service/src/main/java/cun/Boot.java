package cun;

/**
 * Created with Project: weather
 * Date: 2017/12/1　11:30
 * Aauthor: Casey
 * Description: https://www.seniverse.com/doc
 */
public class Boot {
    public static void main(String[] args) {
        CityWeatherOutPut shenzhen = new CityWeatherOutPut("shenzhen");
        CityWeatherOutPut binzhou = new CityWeatherOutPut("binzhou");
        CityWeatherOutPut beijing = new CityWeatherOutPut("beijing");
        CityWeatherOutPut nanjing = new CityWeatherOutPut("nanjing");

        while (true) {
            shenzhen.outPut();
            binzhou.outPut();
            beijing.outPut();
            nanjing.outPut();

            try {
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
