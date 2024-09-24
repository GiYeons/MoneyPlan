package com.moneyplan.expense.domain;

import com.moneyplan.category.domain.Category;
import com.moneyplan.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private LocalDateTime spentAt;

    @Column(nullable = false)
    private int amount;

    @Column
    private String memo;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("0")
    private boolean isTotalExcluded;

    @Builder
    public Expense(Member member, Category category, LocalDateTime spentAt, int amount, String memo,
        boolean isTotalExcluded) {
        this.member = member;
        this.category = category;
        this.spentAt = spentAt;
        this.amount = amount;
        this.memo = memo;
        this.isTotalExcluded = isTotalExcluded;
    }
}
