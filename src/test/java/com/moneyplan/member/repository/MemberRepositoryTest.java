package com.moneyplan.member.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.moneyplan.common.TestConfig;
import com.moneyplan.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("MemberRepository 테스트")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Member 저장 : 성공")
    void save_whenValid() {
        // given
        Member member = Member.builder()
            .account("MoneyPlan")
            .password("password")
            .build();

        // when
        Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getAccount()).isEqualTo(member.getAccount());

    }
}