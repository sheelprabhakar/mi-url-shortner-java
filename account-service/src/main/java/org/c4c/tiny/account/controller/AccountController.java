package org.c4c.tiny.account.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.c4c.tiny.account.domain.Role;
import org.c4c.tiny.account.mapper.ModelMapper;
import org.c4c.tiny.account.model.UserResource;
import org.c4c.tiny.account.model.UserResponseResource;
import org.c4c.tiny.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountService _accountService;

	@Autowired
	private ModelMapper _mapper;

	@PostMapping("/signin")
	public String login(@RequestParam String username, @RequestParam String password) {

		return _accountService.signin(username, password);
	}

	@PostMapping("/signup")
	public @ResponseBody ResponseEntity<String> signup(@RequestBody UserResource user) {
		if (user.getRoles() == null) {
			List<Role> roles = new ArrayList<>();
			roles.add(Role.ROLE_CLIENT);
			user.setRoles(roles);
		}
		return new ResponseEntity<String>(_accountService.signup(_mapper.mapUser(user)), HttpStatus.OK);

	}

	@DeleteMapping(value = "/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String delete(@PathVariable String username) {
		_accountService.delete(username);
		return username;
	}

	@GetMapping(value = "/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserResponseResource search(@PathVariable String username) {
		return _mapper.mapUser(_accountService.find(username));
	}

	@GetMapping(value = "/me")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public ResponseEntity<UserResponseResource> findme(HttpServletRequest req) {
		return new ResponseEntity<UserResponseResource>(_mapper.mapUser(_accountService.findme(req)), HttpStatus.OK);
	}

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public String refresh(HttpServletRequest req) {
		return _accountService.refresh(req.getRemoteUser());
	}

}
