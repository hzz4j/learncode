package org.hzz.strategy.v2.zombie;

import org.hzz.strategy.v2.action.attack.Attackable;
import org.hzz.strategy.v2.action.attack.BiteAttack;
import org.hzz.strategy.v2.action.move.Moveable;
import org.hzz.strategy.v2.action.move.StepByStepMove;

public class NormalZombie extends Zombie{
    public NormalZombie(){
        super(new StepByStepMove(),new BiteAttack());
    }

    public NormalZombie(Moveable moveable, Attackable attackable) {
        super( moveable, attackable );
    }
    @Override
    public void display() {
        System.out.println("我是普通僵尸.");
    }

    @Override
    public void move() {
        moveable.move();
    }

    @Override
    public void attack() {
        attackable.attack();
    }
}
