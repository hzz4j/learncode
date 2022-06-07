package org.hzz.testset;

import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        List<Instance> newInstance = Arrays.asList(
                new Instance("192.168.187.135",true),
                new Instance("192.168.187.136",true)
        );

        List<Instance> oldInstance = Arrays.asList(
                new Instance("192.168.187.135",false),
                new Instance("192.168.187.136",true)
        );

        Collection intersection = CollectionUtils.intersection(newInstance, oldInstance);
        System.out.println(intersection);
    }
}
/**
 * [192.168.187.136##true]
 */