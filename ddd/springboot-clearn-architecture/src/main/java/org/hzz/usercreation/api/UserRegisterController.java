package org.hzz.usercreation.api;

import org.hzz.usercreation.interactor.UserInputBoundary;
import org.hzz.usercreation.interactor.model.UserRequestModel;
import org.hzz.usercreation.interactor.model.UserResponseModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegisterController {
    final UserInputBoundary userInputBoundary;

    public UserRegisterController(UserInputBoundary userInputBoundary) {
        this.userInputBoundary = userInputBoundary;
    }

    @PostMapping("/user")
    public UserResponseModel create(@RequestBody UserRequestModel userRequestModel) {
        return userInputBoundary.create(userRequestModel);
    }
}
