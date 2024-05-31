package com.spring.practice.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @NoArgsConstructor 사용 이유?
 * - JPA의 기본 스펙에 따라, 엔티티 클래스는 기본 생성자 필요 + JPA가 리플랙션 사용하여 객체 사용 시 기본 생성자 사용
 * - JPA는 지연 로딩을 위해 프록시 객체 생성 -> 이때 기본 생성자 사용 -> 기본 생성자 없으면 오류 발생 할 수 있음
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    // org.h2.jdbc.JdbcSQLSyntaxErrorException: Sequence "HIBERNATE_SEQUENCE" not found; SQL statement:
    // https://mungmange.tistory.com/entry/Sequence-HIBERNATESEQUENCE-not-found
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private Integer memberAge;

    @Builder
    public Member(Long id, String memberName, Integer memberAge) {
        this.id = id;
        this.memberName = memberName;
        this.memberAge = memberAge;
    }

    /**
     * 회원 정보 수정 - Dirty Checking
     */
    public void updateMember(String memberName, Integer memberAge) {
        this.memberName = memberName;
        this.memberAge = memberAge;
    }
}