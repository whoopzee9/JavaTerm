package com.course.repository.jpa;

import com.course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> { //JpaRepository<User, Long>
    Optional<User> findUserByUserName(String userName);
}
