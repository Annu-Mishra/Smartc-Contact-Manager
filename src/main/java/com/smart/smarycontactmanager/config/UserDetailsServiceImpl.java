package com.smart.smarycontactmanager.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.smarycontactmanager.smartdao.UserRepository;

import com.smart.smarycontactmanager.smartentities.User;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
       User user=userRepository.getUserByUserName(username);

       if(user==null){
        throw new UsernameNotFoundException("Could not found user !!");
       }

       CustumUserDetails custumUserDetails=new CustumUserDetails(user);
       return custumUserDetails;

        
        
    }
    
}
