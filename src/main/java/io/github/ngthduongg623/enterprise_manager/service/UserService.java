package io.github.ngthduongg623.enterprise_manager.service;

import io.github.ngthduongg623.enterprise_manager.entity.User;
import io.github.ngthduongg623.enterprise_manager.security.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	User findByUserName(String userName);
	void save(WebUser webUser);


}