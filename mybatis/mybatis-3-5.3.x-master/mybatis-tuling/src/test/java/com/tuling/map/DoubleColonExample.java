package com.tuling.map;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 *
 * 双冒号语法
 * 1.静态方法引用（static method）语法：classname::methodname 例如：Person::getAge
 * 2.对象的实例方法引用语法：instancename::methodname 例如：System.out::println
 * 3.对象的超类方法引用语法： super::methodname
 * 4.类构造器引用语法： classname::new 例如：ArrayList::new
 * 5.数组构造器引用语法： typename[]::new 例如： String[]:new
 */
public class DoubleColonExample extends BaseExample{


    /**
     * 1.静态方法引用（static method）语法：classname::methodname 例如：Person::getAge
     */
    @Test
    public void test01() {
        List<String> list = Arrays.asList("aaaa", "bbbb", "cccc");

        //静态方法语法	ClassName::methodName
        list.forEach(DoubleColonExample::printStatic);
    }

    public static void printStatic(String content){
        System.out.println(content);
    }

    /**
     * 2.对象的实例方法引用语法：new instancename::methodname 例如：System.out::println
     */
    @Test
    public void test02() {
        List<String> list = Arrays.asList("aaaa", "bbbb", "cccc");

        //对象实例语法	instanceRef::methodName
        list.forEach(new DoubleColonExample()::print);
    }

    public void print(String content){
        System.out.println(content);
    }


    /**
     * 3.对象的超类方法引用语法： super::methodname
     */
    @Test
    public void test03() {
        List<String> list = Arrays.asList("aaaa", "bbbb", "cccc");

        //对象的超类方法语法： super::methodName
        list.forEach(super::print);
    }


    /**
     * 4.类构造器引用语法： classname::new 例如：ArrayList::new
     */
    @Test
    public void test04() {
        InterfaceExample com =  DoubleColonExample::new;
        DoubleColonExample bean = com.create();
        System.out.println(bean);
    }


    /**
     * 数组构造器引用语法： typename[]::new 例如： String[]:new
     */
    @Test
    public  void test05(){
        Function<Integer, DoubleColonExample[]> function = DoubleColonExample[]::new;
        DoubleColonExample[] array = function.apply(4);	//这里的4是数组的大小

        for(DoubleColonExample e:array){
            System.out.println(e);	//如果输出的话，你会发现会输出4个空对象(null)
        }
    }
}

class BaseExample {
    public void print(String content){
        System.out.println(content);
    }
}

interface InterfaceExample{
    DoubleColonExample create();
}


