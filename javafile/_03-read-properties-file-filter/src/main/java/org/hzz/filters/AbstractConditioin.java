package org.hzz.filters;

import org.hzz.outcome.OutcomesResolver;
import org.hzz.outcome.StandardOutcomesResolver;
import org.hzz.outcome.ThreadedOutcomesResolver;

import java.util.List;
import java.util.Properties;

public abstract class AbstractConditioin implements Filter{
    private final String value;

    public AbstractConditioin(String value) {
        this.value = value;
    }

    @Override
    public boolean[] filter(List<String> configurations, Properties metadata) {
        boolean[] match = new boolean[configurations.size()];

        int split = configurations.size()/ 2;

        // 子线程执行，在构造方法中
        OutcomesResolver first = createOutcomesResolver(configurations, metadata, this.value, 0, split);
        OutcomesResolver second = new StandardOutcomesResolver(configurations, metadata, this.value, split, configurations.size());
        // 主线程执行
        boolean[] secondHalf = second.resolveOutcomes();
        // 等待子线程执行完毕
        boolean[] firstHalf = first.resolveOutcomes();

        System.arraycopy(firstHalf, 0, match, 0, firstHalf.length);
        System.arraycopy(secondHalf, 0, match, split, secondHalf.length);
        return match;
    }

    public OutcomesResolver createOutcomesResolver(List<String> configurations, Properties metadata,
                                                   String key,int start, int end){
        OutcomesResolver outcomesResolver =  new StandardOutcomesResolver(configurations, metadata, key, start, end);

        return new ThreadedOutcomesResolver(outcomesResolver);
    }
}
