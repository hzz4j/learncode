package org.hzz.observer;

import org.hzz.observer.impl.SubjectImp;
import org.hzz.observer.impl.Task;

public class TestMain {
    public static void main(String[] args) {
        Subject subject = new SubjectImp();
        ObServer task1 = new Task("Task1");
        ObServer task2 = new Task("Task2");

        subject.attach(task1);
        subject.attach(task2);
        subject.notifyAllObServer("A happed");
        System.out.println("--------------------------------------------");
        subject.remove(task2);
        subject.notifyAllObServer("A happed");
    }
}
