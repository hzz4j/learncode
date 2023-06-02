package org.hzz;

import org.hzz.usercreation.entity.User;
import org.hzz.usercreation.entity.impl.CommonUserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CommonUserTest {

    @Test
    public void given123Password_whenPasswordIsNotValid_thenIsFalse(){
        User user = new CommonUserFactory().createUser("Q10Viking", "123");
        assertFalse(user.passwordIsValid());
    }
}
