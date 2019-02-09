package org.c4c.tiny.account.mapper;

import org.c4c.tiny.account.domain.User;
import org.c4c.tiny.account.model.UserResource;
import org.c4c.tiny.account.model.UserResponseResource;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

	public User mapUser(UserResource user) {
		User result = new User();
		result.setEmail(user.getEmail());
		result.setUsername(user.getUsername());
		result.setPassword(user.getPassword());
		result.setRoles(user.getRoles());
		return result;
	}

	public UserResponseResource mapUser(User user) {
		UserResponseResource resource = new UserResponseResource();
		resource.setEmail(user.getEmail());
		resource.setId(user.getId());
		resource.setRoles(user.getRoles());
		resource.setUsername(user.getUsername());

		return resource;
	}

}
