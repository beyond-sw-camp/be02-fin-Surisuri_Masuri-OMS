package com.example.Surisuri_Masuri.member.Repository;

import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String userEmail);

    List<User> findByUserName(String userName);

    Optional<User> findByUserPhoneNo(String userPhoneNo);

}