package org.hzz.complex;

import javax.validation.Valid;

public interface IUserService {
    void saveUser(@Valid User user);

    @Valid User getUser();
}
