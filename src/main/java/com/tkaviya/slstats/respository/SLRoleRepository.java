package com.tkaviya.slstats.respository;

/***************************************************************************
 * Created:     10 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

import java.util.Optional;

import com.tkaviya.slstats.model.SLERole;
import com.tkaviya.slstats.model.SLRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SLRoleRepository extends JpaRepository<SLRole, Long> {
    Optional<SLRole> findByName(SLERole name);
}
