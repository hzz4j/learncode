package cn.tuling.localservice;

import cn.tuling.localservice.local.LocalCall;
import cn.tuling.localservice.local.ThreadCall;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocalServiceApplicationTests {

    @Autowired
    private LocalCall localCall;

    @Autowired
    private ThreadCall threadCall;

    @Test
    void testLocal() {
        localCall.processOrder();
    }

    @Test
    void testThreadCall() throws InterruptedException {
        threadCall.processOrder();
        Thread.sleep(100);
    }

}
