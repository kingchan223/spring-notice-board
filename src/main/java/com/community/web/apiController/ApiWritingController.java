package com.community.web.apiController;

import com.community.domain.dto.writing.WritingDto;
import com.community.domain.entity.Writing;
import com.community.service.member.MemberService;
import com.community.service.writing.WritingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.community.domain.dto.writing.WritingDto.createWritingDto;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiWritingController {

    private final WritingService writingService;
    private final MemberService memberService;

    @GetMapping("/api/board")
    public ResponseEntity<?> boardsAll(){
        List<Writing> result = writingService.findAll();
        List<WritingDto> wrtings = new ArrayList<>();
        for (Writing writing : result) {
            wrtings.add(createWritingDto(writing));
        }
        return new ResponseEntity<>(wrtings, HttpStatus.OK);
    }

    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> boardOne(@PathVariable Long id){
        Writing result = writingService.findOne(id);
        return new ResponseEntity<>(createWritingDto(result), HttpStatus.OK);
    }
}
