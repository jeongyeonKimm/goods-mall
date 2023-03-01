package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


   Member findByEmail(String email);
   Boolean existsByEmail(String email);
   Member findByEmailAndPassword(String email, String password);
   Member findByNameAndBirth(String name, Date birth);
}
