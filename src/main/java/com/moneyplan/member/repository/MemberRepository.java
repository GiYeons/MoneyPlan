package com.moneyplan.member.repository;

import com.moneyplan.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByAccount(String account);

    Boolean existsByAccount(String account);

}
