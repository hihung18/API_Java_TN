package com.example.api_java.security.services;

import com.example.api_java.model.entity.UserDetail;
import com.example.api_java.repository.IUserDetailRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserDetailRepository userDetailRepository;

    public UserDetailsServiceImpl(IUserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserDetail user = (UserDetail) userDetailRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );

        return UserDetailsImpl.build(user);
    }


}
