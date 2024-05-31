package com.spring.practice.service;

import com.spring.practice.domain.member.entity.Member;
import com.spring.practice.domain.member.repository.MemberRepository;
import com.spring.practice.web.dto.request.MemberRequest;
import com.spring.practice.web.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * @Cacheable
     * - Spring Boot에서 해당 어노테이션을 통해 캐싱 기능 제공
     * - 캐시는 메서드의 리턴(return)값 과, 파라미터를 저장할 내용으로 주 타깃으로 삼는다
     * - 보통 함수 단위에 설정, 클래스 + 인터페이스에 캐시 하는일은 드문 일
     * - @Cacheable -> 캐싱 수행 or 캐싱 적용 어노테이션
     *
     * @Mechanism
     *  - 1) 함수 호출 시 먼저 캐시에서 해당 키 검색
     *  - 2) 키가 존재하면 캐시된 값 반환, 함수는 실행되지 않음
     *  - 3) 키가 존재하지 않으면 함수 실행 후 반환값을 캐시에 저장
     *  - 4) unless 속성을 통해 캐시에 저장하지 않을 조건 지정 가능
     *
     * value
     * - 캐시의 이름 지정(캐시로 사용할 이름)
     *
     * key
     * - 캐시의 키 지정(캐시의 키로 사용할 값), 해당 key로 캐시에 저장된 데이터를 찾음
     *
     * unless
     * - 캐시의 조건 지정(캐시에 저장할 조건 지정), 해당 조건이 true면 캐시에 저장하지 않음
     * - 여기서 #result 는 해당 함수의 반환값(return)을 의미
     */
    @Cacheable(value = "member-id-caches", key = "#id", unless = "#result.id == null")
    public MemberResponseDto.MemberInfo getMemberById(Long id) {
        log.info("[Cache::member-id-caches] getMemberById -> id = {}", id);
        return memberRepository.findById(id)
                .map(MemberResponseDto.MemberInfo::toDto)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 회원이 존재하지 않습니다."));
    }

    /**
     * @CachePut
     * - 메서드를 항상 실행하고 결과를 캐시에 저장
     * - 이미 캐시된 값이 있으면 덮어 씌운다
     * - 캐시된 값을 UPDATE(업데이트) 해야 하는 경우 사용
     *
     * @Mechanism
     * - 1) 함수 호출 시 항상 실행
     * - 2) 메서드 실행 결과를 캐시에 저장 후 캐시 데이터 갱신
     * - 3) 데이터의 UPDATE(업데이트) 발생 시 사용
     */
    @CachePut(value = "member-id-caches", key = "#id")
    @Transactional
    public MemberResponseDto.MemberInfo updateMemberById(Long id, MemberRequest.MemberUpdate memberUpdate) {
        log.info("[Cache::member-id-caches] updateMemberById -> id = {}", id);
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id의 회원이 존재하지 않습니다."));
        member.updateMember(memberUpdate.getMemberName(), memberUpdate.getMemberAge());
        return MemberResponseDto.MemberInfo.toDto(member);
    }

    /**
     * @CacheEvict
     * - 기본적으로 메서드의 키에 해당하는 캐시 제거
     * - 만약 아래와 같이 메서드에 @CacheEvict 적용 하면 같은 id 값을 가진 데이터만 캐시에서 삭제
     * - id 기반으로 하여 특정 데이터 DB에서 삭제 시 캐시의 id 값도 삭제하는 경우 사용할 수 있을 듯
     */
    @CacheEvict(value = "member-id-caches", key = "#id")
    @Transactional
    public void deleteMemberById(Long id) throws InterruptedException {
        log.info("[Cache::member-id-caches] deleteMemberById -> id = {}", id);
        memberRepository.deleteById(id);
    }

    /**
     * allEntries = true: 해당 캐시의 모든 데이터를 삭제
     */
    @CacheEvict(value = "member-id-caches", allEntries = true)
    public void clearMemberIdAll() {
        log.info("[Cache::member-id-caches] clear all caches");
    }

    /**
     * beforeInvocation = true: 메서드 실행 '전'에 캐시를 삭제
     * beforeInvocation = false: 메서드 실행 '후'에 캐시를 삭제
     */
    @CacheEvict(value = "member-id-caches", beforeInvocation = true)
    public void clearMemberIdBeforeInvocation() {
        log.info("[Cache::member-id-caches] clear all caches before invocation");
    }

    /**
     * 회원 저장용
     */
    @Transactional
    public Long saveMember(MemberRequest.MemberSave memberSave) {
        Member saveMember = memberRepository.save(memberSave.toEntity());
        return saveMember.getId();
    }
}