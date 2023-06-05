package org.hzz.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = "str.queue")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }

    @JmsListener(destination = "entity.queue")
    public void receiveMessage(org.hzz.entity.Email email) {
        System.out.println("Received <" + email + ">");
    }
}
