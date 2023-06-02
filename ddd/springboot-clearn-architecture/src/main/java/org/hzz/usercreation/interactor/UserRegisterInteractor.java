package org.hzz.usercreation.interactor;

import lombok.AllArgsConstructor;
import org.hzz.usercreation.entity.User;
import org.hzz.usercreation.entity.UserFactory;
import org.hzz.usercreation.interactor.gateway.UserRegisterDsGateway;
import org.hzz.usercreation.interactor.gateway.impl.JpaUser;
import org.hzz.usercreation.interactor.model.UserDsRequestModel;
import org.hzz.usercreation.interactor.model.UserRequestModel;
import org.hzz.usercreation.interactor.model.UserResponseModel;
import org.hzz.usercreation.interactor.view.UserPresenter;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UserRegisterInteractor implements UserInputBoundary{
    private final UserRegisterDsGateway userRegisterDsGateway;
    private final UserPresenter userPresenter;
    private final UserFactory userFactory;
    @Override
    public UserResponseModel create(UserRequestModel userRequestModel) {
        if(userRegisterDsGateway.existByName(userRequestModel.getName())){
            return userPresenter.prepareFailView("user already exist");
        }

        User user = userFactory.createUser(userRequestModel.getName(), userRequestModel.getPassword());
        if(!user.passwordIsValid()){
            return userPresenter.prepareFailView("User password must have more than 5 characters");
        }

        UserDsRequestModel userDsRequestModel = new UserDsRequestModel(
                user.getName(),
                user.getPassword(),
                LocalDateTime.now()
        );

        userRegisterDsGateway.save(userDsRequestModel);

        UserResponseModel userResponseModel = new UserResponseModel(
                userDsRequestModel.getName(),
                userDsRequestModel.getCreationTime().toString()
        );
        return userPresenter.prepareSuccessView(userResponseModel);
    }
}
