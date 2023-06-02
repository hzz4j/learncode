package org.hzz.usercreation.interactor.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDsRequestModel {
    private final String name;
    private final String password;
    private final LocalDateTime creationTime;
}
