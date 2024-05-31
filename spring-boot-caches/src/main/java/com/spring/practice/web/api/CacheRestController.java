package com.spring.practice.web.api;

import com.spring.practice.service.MemberService;
import com.spring.practice.web.dto.request.MemberRequest;
import com.spring.practice.web.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@RestController
public class CacheRestController {

    private final MemberService memberService;

    /**
     * 단일 회원 조회
     * @param id : 회원 id
     */
    @GetMapping(value = "/member/{id}")
    public ResponseEntity<MemberResponseDto.MemberInfo> getMemberById(@PathVariable("id") Long id) {
        log.info("[Select One] Test cache, id = {}", id);
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /**
     * 단일 회원 수정
     * @param id : 회원 id
     */
    @PutMapping(value = "/member/{id}")
    public ResponseEntity<MemberResponseDto.MemberInfo> updateMemberById(
            @PathVariable("id") Long id,
            @RequestBody @Valid MemberRequest.MemberUpdate memberUpdate,
            BindingResult bindingResult) {
        log.info("[Update] Test cache, id = {}", id);
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(memberService.updateMemberById(id, memberUpdate));
    }

    /**
     * 단일 회원 삭제
     * @param id : 회원 id
     */
    @DeleteMapping(value = "/member/{id}")
    public void deleteMemberById(@PathVariable("id") Long id) throws InterruptedException {
        log.info("[Delete] Test cache, id = {}", id);
        memberService.deleteMemberById(id);
    }

    /**
     * 캐시 전체 초기화(삭제)
     */
    @PostMapping(value = "/member/management-caches/all/clear")
    public void clearMemberIdAll() {
        log.info("[Clear] Test cache, all clear");
        memberService.clearMemberIdAll();
    }

    /**
     * 캐시 초기화(함수 호출 전 캐시 삭제 후 로직 진행)
     */
    @PostMapping(value = "/member/management-caches/before/clear")
    public void clearMemberIdBeforeInvocation() {
        log.info("[Clear] Test cache, before clear");
        memberService.clearMemberIdBeforeInvocation();
    }

    /**
     * 회원 등록
     * @param memberSave : 회원 등록 정보
     */
    @PostMapping(value = "/member")
    public ResponseEntity<Long> saveMember(
            @RequestBody @Valid MemberRequest.MemberSave memberSave,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(memberService.saveMember(memberSave));
    }
}