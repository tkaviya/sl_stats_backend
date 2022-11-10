package com.tkaviya.slstats.respository;

/***************************************************************************
 * Created:     10 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

import java.util.Optional;

import com.tkaviya.slstats.model.SLUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SLUserRepository extends JpaRepository<SLUser, Long> {
    Optional<SLUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
