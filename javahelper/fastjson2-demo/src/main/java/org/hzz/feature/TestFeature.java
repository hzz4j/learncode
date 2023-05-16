package org.hzz.feature;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.reader.ObjectReaderProvider;
import com.alibaba.fastjson2.writer.ObjectWriterProvider;
import org.junit.Test;

public class TestFeature {
    @Test
    public void test(){
        Goods goods = new Goods();
        goods.setName("Java编程思想");
        goods.setPrice(100d);
        goods.setAuthor("Bruce Eckel");
        System.out.println(JSON.toJSONString(goods));
    }

    @Test
    public void test_context(){
        //配置
        // 使用下划线命名法
        ObjectWriterProvider provider = new ObjectWriterProvider(PropertyNamingStrategy.SnakeCase);
        // 配置feature
        JSONWriter.Context context = new JSONWriter.Context(provider,
                JSONWriter.Feature.NullAsDefaultValue,
                JSONWriter.Feature.PrettyFormat
        );

        Goods goods = new Goods();
        goods.setName("Java编程思想");
        goods.setPrice(100d);
        goods.setAuthor("Bruce Eckel");

        System.out.println(JSON.toJSONString(goods, context));
    }


    /**
     * {
     * 	"sub_goods":[],
     * 	"name":"Java编程思想",
     * 	"authorAlias":"Bruce Eckel"
     * }
     */

    @Test
    public void test_context2(){
        // 配置feature
        JSONReader.Context context = new JSONReader.Context(
                JSONReader.Feature.SupportSmartMatch, // 自动匹配snake, camel等
                JSONReader.Feature.IgnoreSetNullValue,
                JSONReader.Feature.InitStringFieldAsEmpty
        );

        String json = "{\"name\":\"Java编程思想\",\"authorAlias\":\"Bruce Eckel\"}";
        Goods goods = JSON.parseObject(json, Goods.class,context);
        System.out.println(goods);
        // Goods(name=Java编程思想, author=Bruce Eckel, price=null, subGoods=[])
    }




    @Test
    public void test_camel_to_snakeCase(){
        //配置
        // 使用下划线命名法
        ObjectWriterProvider provider = new ObjectWriterProvider(PropertyNamingStrategy.SnakeCase);
        // 配置feature
        JSONWriter.Context context = new JSONWriter.Context(provider, JSONWriter.Feature.PrettyFormat);

        User user = new User("Q10Viking", "123456");
        System.out.println(JSON.toJSONString(user, context));
    }

    /**
     * {
     * 	"pass_word":"123456",
     * 	"user_name":"Q10Viking"
     * }
     */

    @Test
    public void test_snake_to_camel_case(){
        // 配置feature
        JSONReader.Context context = new JSONReader.Context(
                JSONReader.Feature.SupportSmartMatch, // 自动匹配snake, camel等
                JSONReader.Feature.IgnoreSetNullValue,
                JSONReader.Feature.InitStringFieldAsEmpty
        );

        String json = "{\"pass_word\":\"123456\",\"user_name\":\"Q10Viking\"}";
        User user = JSON.parseObject(json, User.class,context);
        System.out.println(user); // User(userName=Q10Viking, passWord=123456)
    }

}
