package com.community.web.controller.errorTestController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ErrorTestController {

    @RequestMapping("/error/test-ex")
    public void Exception(){
        throw new RuntimeException();
    }

    @RequestMapping("/error/test-404")
    public void Exception(HttpServletResponse response) throws IOException {
        response.sendError(404,"404에러" );
    }
}
