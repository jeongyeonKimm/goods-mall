package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


   Member findByEmail(String email);
   Boolean existsByEmail(String email);
   Member findByEmailAndPassword(String email, String password);
}
