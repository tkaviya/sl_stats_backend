package com.tkaviya.slstats.security;

import com.tkaviya.slstats.model.SLUser;
import com.tkaviya.slstats.respository.SLUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/***************************************************************************
 * Created:     01 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Service
public class SLUserDetailsService implements UserDetailsService {

    private final SLUserRepository slUserRepository;

    public SLUserDetailsService(SLUserRepository slUserRepository) {
        this.slUserRepository = slUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SLUser slUser = slUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found with username: " + username));

        return SLUserDetailsImpl.build(slUser);
    }
}
