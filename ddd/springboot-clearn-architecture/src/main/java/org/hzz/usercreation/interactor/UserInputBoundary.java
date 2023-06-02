package org.hzz.usercreation.interactor;

import org.hzz.usercreation.interactor.model.UserRequestModel;
import org.hzz.usercreation.interactor.model.UserResponseModel;

public interface UserInputBoundary {
    UserResponseModel create(UserRequestModel userRequestModel);
}
