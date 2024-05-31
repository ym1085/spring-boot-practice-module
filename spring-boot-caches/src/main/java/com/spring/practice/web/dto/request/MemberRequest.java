package com.spring.practice.web.dto.request;

import com.spring.practice.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MemberRequest {

    @Getter
    @Setter
    public static class MemberSave {
        @NotBlank(message = "회원명은 필수 입력 값입니다.")
        private String memberName;

        @NotNull(message = "회원 나이는 필수 입력 값입니다.")
        private Integer memberAge;

        public Member toEntity() {
            return Member.builder()
                    .memberName(memberName)
                    .memberAge(memberAge)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class MemberUpdate {
        @NotBlank(message = "회원명은 필수 입력 값입니다.")
        private String memberName;

        @NotNull(message = "회원 나이는 필수 입력 값입니다.")
        private Integer memberAge;

        public Member toEntity() {
            return Member.builder()
                    .memberName(memberName)
                    .memberAge(memberAge)
                    .build();
        }
    }
}