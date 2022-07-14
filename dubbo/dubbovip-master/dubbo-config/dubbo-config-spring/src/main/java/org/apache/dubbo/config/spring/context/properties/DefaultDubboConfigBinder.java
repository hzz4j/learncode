/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.config.spring.context.properties;

import org.apache.dubbo.config.AbstractConfig;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

import java.util.Map;

import static org.apache.dubbo.config.spring.util.PropertySourcesUtils.getSubProperties;

/**
 * Default {@link DubboConfigBinder} implementation based on Spring {@link DataBinder}
 */
public class DefaultDubboConfigBinder extends AbstractDubboConfigBinder {

    @Override
    public <C extends AbstractConfig> void bind(String prefix, C dubboConfig) {
        DataBinder dataBinder = new DataBinder(dubboConfig);
        // Set ignored*
        dataBinder.setIgnoreInvalidFields(isIgnoreInvalidFields());
        dataBinder.setIgnoreUnknownFields(isIgnoreUnknownFields());
        // Get properties under specified prefix from PropertySources
        // getPropertySources()会拿到由@PropertySource注入进来的properties文件中的内容
        // 同时还包括当前java的所有环境变量，包括手动通过-D添加的配置

        Map<String, Object> properties = getSubProperties(getPropertySources(), prefix);
        // Convert Map to MutablePropertyValues
        MutablePropertyValues propertyValues = new MutablePropertyValues(properties);
        // Bind
        dataBinder.bind(propertyValues);
    }

}

