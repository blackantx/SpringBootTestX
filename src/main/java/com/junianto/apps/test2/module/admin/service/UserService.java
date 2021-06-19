package com.junianto.apps.test2.module.admin.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.junianto.apps.test2.model.Role;
import com.junianto.apps.test2.model.User;
import com.junianto.apps.test2.repository.RoleRepository;
import com.junianto.apps.test2.repository.UserRepository;



@Service("userService")
public class UserService {
	private final Logger log = LogManager.getLogger(this.getClass());

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	public User saveUser(User user) {
		log.debug("saveUser: "+user.getRoleStr());
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole(user.getRoleStr());
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		return userRepository.save(user);
	}

}