package org.hzz.outcome;

import java.util.List;
import java.util.Properties;

public class StandardOutcomesResolver implements OutcomesResolver {
    private final List<String> configurations;
    private final Properties metadata;
    private final String key;

    private final int start;

    private final int end;

    public StandardOutcomesResolver(List<String> configurations, Properties metadata,
                                    String key,int start, int end) {
        this.configurations = configurations;
        this.metadata = metadata;
        this.key = key;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean[] resolveOutcomes() {
        boolean outcomes[] = new boolean[this.end - this.start];
        for (int i = this.start; i < this.end; i++) {
            String className = this.configurations.get(i);
            String value = get(className);
            outcomes[i - this.start] = (value != null);
        }
        return outcomes;
    }

    public String get(String className) {
       return this.metadata.getProperty(className + "." + this.key);
    }
}
