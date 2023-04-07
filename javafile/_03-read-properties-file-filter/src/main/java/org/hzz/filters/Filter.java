package org.hzz.filters;

import java.util.List;
import java.util.Properties;

public interface Filter {
    boolean[] filter(List<String> configurations, Properties metadata);
}
