package org.hzz.usercreation.persistence.repository;

import org.hzz.usercreation.persistence.mapping.UserDataMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRespository extends JpaRepository<UserDataMapper, String> {
}
