package com.tkaviya.slstats.security;

import com.tkaviya.slstats.model.SLUser;
import com.tkaviya.slstats.respository.SLUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SLUser slUser = slUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found with username: " + username));

        return SLUserDetailsImpl.build(slUser);

//        if ("tkaviya".equals(username)) {
//            return new User("tkaviya", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
    }
}
