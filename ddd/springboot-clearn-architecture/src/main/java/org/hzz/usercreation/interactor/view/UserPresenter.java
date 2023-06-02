package org.hzz.usercreation.interactor.view;

import org.hzz.usercreation.interactor.model.UserResponseModel;

public interface UserPresenter {
    UserResponseModel prepareSuccessView(UserResponseModel user);
    UserResponseModel prepareFailView(String error);
}
