package org.c4c.tiny.account.service;

import javax.servlet.http.HttpServletRequest;

import org.c4c.tiny.account.domain.User;

public interface AccountService {
	String signin(String username, String password);

	String signup(User user);

	void delete(String username);

	User find(String username);

	User findme(HttpServletRequest req);

	String refresh(String username);

}
