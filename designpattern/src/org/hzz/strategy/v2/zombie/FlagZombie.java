package org.hzz.strategy.v2.zombie;

import org.hzz.strategy.v2.action.attack.Attackable;
import org.hzz.strategy.v2.action.attack.BiteAttack;
import org.hzz.strategy.v2.action.move.Moveable;
import org.hzz.strategy.v2.action.move.StepByStepMove;

public class FlagZombie extends Zombie{

    public FlagZombie(){
        super(new StepByStepMove(), new BiteAttack());
    }

    public FlagZombie(Moveable moveable, Attackable attackable){
        super(moveable,attackable);
    }
    @Override
    public void display() {
        System.out.println("我是旗手僵尸.");
    }

    @Override
    public void move() {
        this.getMoveable().move();
    }

    @Override
    public void attack() {
        this.getAttackable().attack();
    }
}
