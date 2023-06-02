package org.hzz.usercreation.interactor.gateway.impl;

import org.hzz.usercreation.interactor.gateway.UserRegisterDsGateway;
import org.hzz.usercreation.interactor.model.UserDsRequestModel;
import org.hzz.usercreation.persistence.mapping.UserDataMapper;
import org.hzz.usercreation.persistence.repository.JpaUserRespository;

public class JpaUser implements UserRegisterDsGateway {
    private final JpaUserRespository jpaUserRespository;
    public JpaUser(JpaUserRespository jpaUserRespository){
        this.jpaUserRespository = jpaUserRespository;
    }
    @Override
    public boolean existByName(String name) {
        return jpaUserRespository.existsById(name);;
    }

    @Override
    public void save(UserDsRequestModel userDsRequestModel) {
        UserDataMapper userDataMapper = new UserDataMapper(
                userDsRequestModel.getName(),
                userDsRequestModel.getPassword(),
                userDsRequestModel.getCreationTime()
        );
        jpaUserRespository.save(userDataMapper);
    }
}
