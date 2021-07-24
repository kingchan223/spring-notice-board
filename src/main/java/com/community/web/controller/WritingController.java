package com.community.web.controller;

import com.community.SessionConst;
import com.community.domain.entity.User;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.service.FileStore;
import com.community.service.UploadFile;
import com.community.service.interfaceService.WritingService;
import com.community.service.interfaceService.UserService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class WritingController {
    private final WritingService writingService;
    private final UserService userService;
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
    public String detail(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) User user,
                         @PathVariable Long writingId, Model model){
        if(user == null){
            return "redirect:/";
        }

        User writingUser = writingService.getOne(writingId).getUser();

        if(user.getEmail().equals(writingUser.getEmail()) && user.getUsername().equals(writingUser.getUsername())){
            model.addAttribute("right", true);
        }

        log.info("user.getEmail()={}", user.getEmail());
        log.info("writingUser.getEmail()={}", writingUser.getEmail());

        log.info("user.getUsername()={}", user.getUsername());
        log.info("writingUser.getUsername()={}", writingUser.getUsername());


        model.addAttribute("writing", writingService.getOne(writingId));
        return "/basic/writing";
    }

    /*글 수정 회면 보여주기*/
    @GetMapping("/edit/{writingId}")
    public String editView(@PathVariable Long writingId,Model model){
        Writing writing = writingService.getOne(writingId);
        model.addAttribute("writing", writing);
        return "/basic/writingEditForm";
    }

    /*글 수정*/
    @PostMapping("/edit/{writingId}")
    public String edit(@PathVariable Long writingId,
                       @Validated @ModelAttribute("writingForm") AddWritingForm addWritingForm){
        /*@@@@@@@@@@@@@@@@@@@수정에도 이미지@@@@@@@@@@@@@@@@@@@@@@@@@*/
        Writing afterWriting = new Writing(addWritingForm.getTitle(), addWritingForm.getContent(), null);
        writingService.update(writingId, afterWriting);
        /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/

        return "redirect:/";
    }

    /*글 작성으로 이동*/
    @GetMapping("writing")
    public String writingForm(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) User user,
            @ModelAttribute("addWritingForm") AddWritingForm addWritingForm){
        if(user == null){
            return "redirect:/";
        }
//        log.info("user.name={}", user.getEmail());
//        log.info("user.name={}", user.getJoinedDate());
//        log.info("user.name={}", user.getId());
//        log.info("user.name={}", user.getLoginId());
//        log.info("user.name={}", user.getPassword());
//        log.info("user.name={}", user.getUsername());
//        log.info("user.name={}", user.getRole());
        return "/basic/writingForm";
    }

    /*글 작성*/
    @PostMapping("writing")
    public String writing(@Validated @ModelAttribute("writingForm") AddWritingForm addWritingForm,
                          BindingResult bindingResult,
                          HttpServletRequest request,
                          Model model,
                          RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "/basic/writingForm";
        }


        //유저찾기
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);


        List<MultipartFile> imageFiles = addWritingForm.getImageFiles();
//        for (MultipartFile imageFile : imageFiles) {
//            System.out.println("imageFile = " + imageFile);
//        }
        List<UploadFile> uploadFiles = fileStore.storeFiles(imageFiles);
        System.out.println("uploadFiles = " + uploadFiles);
        for (UploadFile uploadFile : uploadFiles) {
            System.out.println("uploadFile = " + uploadFile);
        }
        Writing savedWriting = new Writing(addWritingForm.getTitle(), addWritingForm.getContent(), uploadFiles);
        //글 작성자에 유저 등록
        savedWriting.setUser(user);
        //글 저장
        writingService.add(savedWriting);

        model.addAttribute("writings",writingService.getAll());
        model.addAttribute("user", user);

        redirectAttributes.addAttribute("writingId", savedWriting.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/{writingId}";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // sdh1df14-12ee-2353-1593-sd34dfg434.png (filename) - > fileStore.getFullPath(filename)하면
        // file"/Users/leechanyoung/Downloads/image/communityImageFiles/sdh1df14-12ee-2353-1593-sd34dfg434.png 로 바뀐다.
        return new UrlResource("file:"+fileDir+filename);
    }
}
