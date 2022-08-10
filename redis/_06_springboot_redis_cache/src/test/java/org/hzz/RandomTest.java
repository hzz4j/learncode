package org.hzz;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class RandomTest {
    @Test
    public void test(){
        for (int i = 0; i < 10; i++) {
            int r = new Random().nextInt(5);
            System.out.println(r);
        }
    }
}
