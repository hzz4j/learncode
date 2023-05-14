package org.hzz.i18n;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import java.util.Locale;

/**
 * 继承它ResourceBundleMessageInterpolator
 * 是为了使用hirbernate validator提供的默认的国际化,因为他们的文件是ValidationMessages_zh.properties
 * 不像我们自己的文件是user_zh.properties
 * 也就是说我们既能使用我们自己国际化，又能使用hibernate validator提供的默认的国际化
 */
public class MyMessageInterpolator extends ResourceBundleMessageInterpolator {
    private static final String path = "i18n/user";
    // hibernate validator的写法
//    private static final String path = "i18n.user";
    public MyMessageInterpolator(){
        // 指定ResourceBundleLocator
        super(new PlatformResourceBundleLocator(path));
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        String result = super.interpolate(messageTemplate, context);
        return result;
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        // 这个方法没用到
        return null;
    }
}
