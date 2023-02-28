package org.hzz.strategy.v2.action.attack;

public class HitAttack implements Attackable{
    @Override
    public void attack() {
        System.out.println("打.");
    }
}
