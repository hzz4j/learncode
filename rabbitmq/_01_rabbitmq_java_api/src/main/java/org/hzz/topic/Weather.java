package org.hzz.topic;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.hzz.util.RabbitConstant;
import org.hzz.util.RabbitUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Weather {
    public static void main(String[] args) throws Exception {

        Map area = new LinkedHashMap<String, String>();
        area.put("china.hunan.changsha.20201127", "中国湖南长沙20201127天气数据");
        area.put("china.hubei.wuhan.20201127", "中国湖北武汉20201127天气数据");
        area.put("china.hunan.zhuzhou.20201127", "中国湖南株洲20201127天气数据");
        area.put("us.cal.lsj.20201127", "美国加州洛杉矶20201127天气数据");

        area.put("china.hebei.shijiazhuang.20201128", "中国河北石家庄20201128天气数据");
        area.put("china.hubei.wuhan.20201128", "中国湖北武汉20201128天气数据");
        area.put("china.henan.zhengzhou.20201128", "中国河南郑州20201128天气数据");
        area.put("us.cal.lsj.20201128", "美国加州洛杉矶20201128天气数据");


        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        Iterator<Map.Entry<String, String>> itr = area.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> me = itr.next();
            //第一个参数交换机名字   第二个参数作为 消息的routing key
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC,me.getKey() , null , me.getValue().getBytes());

        }

        channel.close();
        connection.close();
    }
}
