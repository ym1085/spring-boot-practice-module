package com.spring.practice.web.dto.response;

import com.spring.practice.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MemberResponseDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class MemberInfo {
        private Long id;
        private String memberName;
        private Integer memberAge;

        public static MemberInfo toDto(Member member) {
            return MemberInfo.builder()
                    .id(member.getId())
                    .memberName(member.getMemberName())
                    .memberAge(member.getMemberAge())
                    .build();
        }
    }
}