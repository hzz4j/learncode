package org.hzz.strategy.v2;

import org.hzz.strategy.v2.action.attack.HitAttack;
import org.hzz.strategy.v2.zombie.NormalZombie;
import org.hzz.strategy.v2.zombie.Zombie;

public class MainTest {
    public static void main(String[] args) {
        Zombie zombie = new NormalZombie();
        zombie.display();
        zombie.move();
        zombie.attack();
        // 改变攻击策略
        zombie.setAttackable(new HitAttack());
        zombie.attack();
    }
}
/**
 * 我是普通僵尸.
 * 一步一步移动.
 * 咬.
 * 打.
 */