package org.hzz.lombok;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestDemo {

    @Test
    public void testResultA() {
        ResultA result1 = new ResultA(new int[]{3, 20});
        ResultA result2 = new ResultA(new int[]{3, 20});
        assertEquals(result1, result2); // 失败
    }

    @Test
    public void testResultB() {
        ResultB result1 = new ResultB(new int[]{3, 20});
        ResultB result2 = new ResultB(new int[]{3, 20});
        assertEquals(result1, result2); // 失败
    }

    @Test
    public void testResultC() {
        ResultC result1 = new ResultC(new int[]{3, 20});
        ResultC result2 = new ResultC(new int[]{3, 20});
        assertEquals(result1, result2); // 成功
    }

    @Test
    public void testResultASolved(){
        ResultA result1 = new ResultA(Arrays.asList(3, 20));
        ResultA result2 = new ResultA(Arrays.asList(3, 20));
        assertEquals(result1, result2); // 成功
    }

    @Test
    public void testResultBSolved() {
        ResultB<List<Integer>> result1 = new ResultB(Arrays.asList(3, 20));
        ResultB<List<Integer>> result2 = new ResultB(Arrays.asList(3, 20));
        assertEquals(result1, result2); // 成功
    }


}
