package com.usertodo.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.usertodo.models.User;
import com.usertodo.repositories.UserRepository;

@Component
public class CustomUserDetailsService  implements UserDetailsService	 {
	
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder bCryptPasswordEncoder;

	    public User findUserByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }

	    public void saveUser(User user) {
	        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        user.setEnabled(true);
	        userRepository.save(user);
	    }

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 User user = userRepository.findByEmail(email);
	        if(user != null) {
	            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	            authorities.add((new SimpleGrantedAuthority("ADMIN")));
	            return buildUserForAuthentication(user, authorities);
	        } else {
	            throw new UsernameNotFoundException("username not found");
	        }
		//return null;
	}
	
	 private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
	        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	    }

}
