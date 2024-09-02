package com.awl.cert.thubalcain.repository.jpa;

import com.awl.cert.thubalcain.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
