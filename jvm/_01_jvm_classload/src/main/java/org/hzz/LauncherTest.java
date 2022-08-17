package org.hzz;

import sun.misc.Launcher;

public class LauncherTest {
    public static void main(String[] args) {
        System.out.println(LauncherTest.class.getClassLoader());
        System.out.println(Launcher.class.getClassLoader());
    }
}
/**
 * sun.misc.Launcher$AppClassLoader@18b4aac2
 * null
 */