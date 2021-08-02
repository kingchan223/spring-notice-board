package com.community.web.controller;

import com.community.SessionConst;
import com.community.domain.entity.Documents;
import com.community.domain.entity.Member;
import com.community.domain.entity.Result;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.service.FileStore;
import com.community.service.writing.WritingService;
import com.community.service.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class WritingController {
    private final WritingService writingService;
    private final MemberService memberService;
    private final FileStore fileStore;

//    @Value("${file.dir}")
    private final String fileDir = "/Users/leechanyoung/Downloads/coding/community/src/main/resources/static/clientImages/";
    private final ObjectMapper objectMapper;

    /*
    *  POST /writings : 글 생성
    *  GET /writings  :  글 생성 화면
    *  GET /writings/{writingNum}  : 글 상세조회
    *  POST /writings/{writingNum}/edit : 글 수정
    *  DELETE /writings/{writingNum} : 글 삭제
    */

    /*글 상세*/
    @GetMapping("{writingId}")
    public String detail(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) Member member,
                         @PathVariable Long writingId, Model model){
        if(member == null){
            return "redirect:/";
        }

        Member writingMember = writingService.findOne(writingId).getMember();

        if(member.getEmail().equals(writingMember.getEmail()) && member.getName().equals(writingMember.getName())){
            model.addAttribute("right", true);
        }

        model.addAttribute("writing", writingService.findOne(writingId));
        return "/basic/writing";
    }

    /*글 수정 회면 보여주기*/
    @GetMapping("/edit/{writingId}")
    public String editView(@PathVariable Long writingId,Model model){
        Writing writing = writingService.findOne(writingId);
        model.addAttribute("writing", writing);
        return "/basic/writingEditForm";
    }

    /*글 수정*/
    @PostMapping("/edit/{writingId}")
    public String edit(@PathVariable Long writingId,
                       @Validated @ModelAttribute("writingForm") AddWritingForm addWritingForm){
        /*@@@@@@@@@@@@@@@@@@@수정에도 이미지@@@@@@@@@@@@@@@@@@@@@@@@@*/
//        Writing afterWriting = new Writing(addWritingForm.getTitle(), addWritingForm.getContent(), null);
//        writingService.update(writingId, afterWriting);
        /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
        return "redirect:/";
    }

    /*글 작성으로 이동*/
    @GetMapping("writing")
    public String writingForm(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) Member member,
            @ModelAttribute("addWritingForm") AddWritingForm addWritingForm){
        if(member == null){
            return "redirect:/";
        }

        return "/basic/writingForm";
    }

    /*글 작성*/
    @PostMapping("writing")
    public String writing(@Validated @ModelAttribute AddWritingForm addWritingForm,
                          BindingResult bindingResult,
                          HttpServletRequest request,
                          Model model,
                          RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "/basic/writingForm";
        }

        //유저찾기
        Member member = findSessionMember(request);
//        Writing savedWriting = new Writing(addWritingForm.getTitle(), addWritingForm.getContent(), uploadFiles);
        //글 작성자에 유저 등록
//        savedWriting.setMember(member);

        //글 저장
        Writing writing = writingService.save(member.getId(), addWritingForm);

        model.addAttribute("writing",writing);
        model.addAttribute("user", member);

        redirectAttributes.addAttribute("writingId", writing.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/{writingId}";
    }

    private Member findSessionMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        return member;
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // sdh1df14-12ee-2353-1593-sd34dfg434.png (filename) - > fileStore.getFullPath(filename)하면
        // file"/Users/leechanyoung/Downloads/image/communityImageFiles/sdh1df14-12ee-2353-1593-sd34dfg434.png 로 바뀐다.
        return new UrlResource("file:"+fileDir+filename);
    }

    @ResponseBody
    @GetMapping("test")
    public Result doTest() {

        WebClient client = WebClient.create("https://dapi.kakao.com/v2/search/web");

        Mono<Result> resultMono = client.get().uri(uriBuilder -> uriBuilder
                .queryParam("query", "스프링")
                .build())
                .header("Authorization", "KakaoAK 0f1c2dc0e54ea9a29fae393f0834cedd")
                .retrieve()
                .bodyToMono(Result.class);

        Result result = resultMono.block();

        ArrayList<Documents> documents = result.getDocuments();
        for (Documents document : documents) {
            System.out.println("===================================");
            System.out.println("document = " + document);
            System.out.println("document.getTitle() = " + document.getTitle());
            System.out.println("document.getContents() = " + document.getContents());
            System.out.println("document.getTitle() = " + document.getTitle());
            System.out.println("document.getUrl() = " + document.getUrl());
        }

        return result;
    }

    @ResponseBody
    @GetMapping("test2")
    public ResponseEntity<Result> doTest2(){

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.set("Authorization", "KakaoAK 0f1c2dc0e54ea9a29fae393f0834cedd");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        HttpEntity entity = new HttpEntity("parameters", headers);

        URI url = URI.create("https://dapi.kakao.com/v2/search/web?query=아이언맨");
        ResponseEntity<Result> response = restTemplate.exchange(url, HttpMethod.GET, entity, Result.class);
        return response;

    }
}
