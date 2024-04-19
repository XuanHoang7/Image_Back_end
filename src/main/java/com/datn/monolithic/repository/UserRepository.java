package com.datn.monolithic.repository;

import com.datn.monolithic.entity.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findOneByUsernameAndPassword(String username, String password);
}
