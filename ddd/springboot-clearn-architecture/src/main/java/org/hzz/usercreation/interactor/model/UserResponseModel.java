package org.hzz.usercreation.interactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseModel {
    private String login;
    private String creationTime;
}
