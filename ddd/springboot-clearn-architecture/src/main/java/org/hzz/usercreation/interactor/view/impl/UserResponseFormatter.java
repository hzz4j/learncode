package org.hzz.usercreation.interactor.view.impl;

import org.hzz.usercreation.interactor.model.UserResponseModel;
import org.hzz.usercreation.interactor.view.UserPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserResponseFormatter implements UserPresenter {
    @Override
    public UserResponseModel prepareSuccessView(UserResponseModel responseModel) {
        LocalDateTime localDateTime = LocalDateTime.parse(responseModel.getCreationTime());
        responseModel.setCreationTime(localDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        return responseModel;
    }

    @Override
    public UserResponseModel prepareFailView(String error) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,error);
    }
}
