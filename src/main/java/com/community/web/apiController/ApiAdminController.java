package com.community.web.apiController;

import com.community.domain.dto.CMRespDto;
import com.community.domain.dto.member.MemberDto;
import com.community.service.member.MemberService;
import com.community.service.writing.WritingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiAdminController {
    private final MemberService memberService;
    private final WritingService writingService;

    @DeleteMapping("/api/admin/board/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id){
        writingService.delete(id);
        return new ResponseEntity<>(new CMRespDto<Integer>(1, "success", null), HttpStatus.OK);
    }
}
