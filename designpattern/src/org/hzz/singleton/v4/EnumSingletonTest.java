package org.hzz.singleton.v4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class EnumSingletonTest {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(EnumSingleton.INSTANCE == EnumSingleton.INSTANCE);
    }
}