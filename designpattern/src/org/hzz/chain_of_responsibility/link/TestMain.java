package org.hzz.chain_of_responsibility.link;

import org.hzz.chain_of_responsibility.link.impl.LogInHandler;
import org.hzz.chain_of_responsibility.link.impl.PermitsHandler;
import org.hzz.chain_of_responsibility.link.impl.RequestFrequentAbstractHandler;
import org.hzz.chain_of_responsibility.link.impl.SensitiveWordsHandler;

import java.util.Arrays;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        Chain chain = new Chain();
        Request request = Request.builder()
                .frequentOk(true)
                .loggedOn(true)
                .isPermits(false)
                .containsSensitiveWords(true)
                .build();

        // build chain
        List<Handler> handlers = Arrays.<Handler>asList(new RequestFrequentAbstractHandler(),
                new LogInHandler(),
                new PermitsHandler(),
                new SensitiveWordsHandler());

        handlers.forEach(handler -> chain.addLast(handler));
        chain.start(request);
    }
}
