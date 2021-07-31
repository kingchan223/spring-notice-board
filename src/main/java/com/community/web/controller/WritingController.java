package com.community.web.controller;

import com.community.SessionConst;
import com.community.domain.entity.Member;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.service.FileStore;
import com.community.service.writing.WritingService;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class WritingController {
    private final WritingService writingService;
    private final MemberService memberService;
    private final FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

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
}
