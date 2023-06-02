package org.hzz.usercreation.interactor.gateway;

import org.hzz.usercreation.interactor.model.UserDsRequestModel;

public interface UserRegisterDsGateway {
    boolean existByName(String name);
    void save(UserDsRequestModel userDsRequestModel);
}
