package org.hzz.strategy.v2.action.attack;

public class BiteAttack implements Attackable{
    @Override
    public void attack() {
        System.out.println("咬.");
    }
}
