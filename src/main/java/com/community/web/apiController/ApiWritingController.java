package com.community.web.apiController;

import com.community.domain.dto.writing.WritingDto;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.service.member.MemberService;
import com.community.service.writing.WritingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.community.domain.dto.writing.WritingDto.createWritingDto;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiWritingController {

    private final WritingService writingService;
    private final MemberService memberService;

    @GetMapping("/api/home")
    public ResponseEntity<?> boardsAll(){
        List<Writing> result = writingService.findAll();
        List<WritingDto> wrtings = new ArrayList<>();
        for (Writing writing : result) {
            wrtings.add(createWritingDto(writing));
        }
        return new ResponseEntity<>(wrtings, HttpStatus.OK);
    }

    // 글 상세
    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> boardOne(@PathVariable Long id){
        Writing result = writingService.findOne(id);
        return new ResponseEntity<>(createWritingDto(result), HttpStatus.OK);
    }

    // 글쓰기
    @PostMapping("/api/board")
    public ResponseEntity<?> addboard(HttpServletRequest request, @RequestBody AddWritingForm addWritingForm) throws IOException {
        Writing writing = writingService.save(7L, addWritingForm);
        return new ResponseEntity<>(createWritingDto(writing), HttpStatus.OK);
    }
}
