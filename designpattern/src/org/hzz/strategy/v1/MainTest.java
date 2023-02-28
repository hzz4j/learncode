package org.hzz.strategy.v1;

import org.hzz.strategy.v1.zombie.AbstractZombie;
import org.hzz.strategy.v1.zombie.FlagZombie;
import org.hzz.strategy.v1.zombie.NormalZombie;

public class MainTest {
    public static void main(String[] args) {
        AbstractZombie normalZombie = new NormalZombie();
        AbstractZombie flagZombie = new FlagZombie();

        normalZombie.display();
        normalZombie.attack();
        normalZombie.move();
        System.out.println("---------------------------------");
        flagZombie.display();
        flagZombie.attack();
        flagZombie.move();

    }
}
/**
 * 我是普通僵尸.
 * 咬.
 * 一步一步移动.
 * ---------------------------------
 * 我是旗手僵尸.
 * 咬.
 * 一步一步移动.
 */