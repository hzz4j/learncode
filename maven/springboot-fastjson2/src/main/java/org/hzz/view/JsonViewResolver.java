package org.hzz.view;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.support.spring.webservlet.view.FastJsonJsonView;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class JsonViewResolver implements ErrorViewResolver {
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        FastJsonJsonView fastJsonJsonView = new FastJsonJsonView();
        Properties properties = new Properties();
        properties.setProperty("code", "404");
        properties.setProperty("message", "Not Found");
        fastJsonJsonView.setAttributes(properties);
        Map<String, Object> map = new HashMap<>();
        map.put("author", "Q10Viking");
        fastJsonJsonView.setRenderedAttributes(CollUtil.set(false, "code", "message", "author"));
        fastJsonJsonView.setAttributesMap(map);
        return new ModelAndView(fastJsonJsonView);
    }
}
