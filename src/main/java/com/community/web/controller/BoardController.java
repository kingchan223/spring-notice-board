package com.community.web.controller;

//
//@Slf4j
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/Board")
public class  BoardController {
//    private final BoardService BoardService;
//    private final MemberService memberService;
//    private final FileStore fileStore;
//
////    @Value("${file.dir}")
//    private final String fileDir = "/Users/leechanyoung/Downloads/coding/community/src/main/resources/static/clientImages/";
//    private final ObjectMapper objectMapper;
//
//    /*
//    *  POST /boards : 글 생성
//    *  GET /boards  :  글 생성 화면
//    *  GET /boards/{boardNum}  : 글 상세조회
//    *  POST /boards/{boardNum}/edit : 글 수정
//    *  DELETE /boards/{boardNum} : 글 삭제
//    */
//
//    /*글 작성으로 이동*/
//    @GetMapping
//    public String boardForm(@ModelAttribute("addboardForm") AddboardForm addboardForm){
//        return "/Board/boardForm";
//    }
//
//    /*글 작성*/
//    @PostMapping
//    public String Board(@AuthenticationPrincipal PrincipalDetails principal,
//                          @Validated @ModelAttribute AddboardForm addboardForm,
//                          BindingResult bindingResult,
//                          HttpServletRequest request,
//                          Model model,
//                          RedirectAttributes redirectAttributes) throws IOException {
//        if (bindingResult.hasErrors()) {
//            log.info("bindingResult={}", bindingResult);
//            return "/Board/boardForm";
//        }
//
//        //유저찾기
//        Member member = memberService.findById(principal.getUsername());
//
//        //글 저장
//        Board Board = BoardService.save(member.getId(), addboardForm);
//
//        model.addAttribute("Board",Board);
//        model.addAttribute("user", member);
//
//        redirectAttributes.addAttribute("boardId", Board.getId());
//        redirectAttributes.addAttribute("status", true);
//
//        return "redirect:/Board/{boardId}";
//    }
//
//    /*글 상세*/
//    @GetMapping("/{boardId}")
//    public String detail(@AuthenticationPrincipal PrincipalDetails principal,
//                         @PathVariable Long boardId, Model model){
//
//
//        Member boardMember = BoardService.findOne(boardId).getMember();
//
//        if(principal.getEmail().equals(boardMember.getEmail()) && principal.getName().equals(boardMember.getName())){
//            model.addAttribute("right", true);
//        }
//
//        model.addAttribute("Board", BoardService.findOne(boardId));
//        return "/Board/Board";
//    }
//
//    /*글 수정 회면 보여주기*/
//    @GetMapping("/edit/{boardId}")
//    public String editView(@PathVariable Long boardId,Model model){
//        Board Board = BoardService.findOne(boardId);
//        model.addAttribute("Board", Board);
//        return "/Board/boardEditForm";
//    }
//
//    /*글 수정*/
//    @PostMapping("/edit/{boardId}")
//    public String edit(@PathVariable Long boardId,
//                       @Validated @ModelAttribute("boardForm") AddboardForm addboardForm){
//        /*@@@@@@@@@@@@@@@@@@@수정에도 이미지@@@@@@@@@@@@@@@@@@@@@@@@@*/
////        Board afterboard = new Board(addboardForm.getTitle(), addboardForm.getContent(), null);
////        BoardService.update(boardId, afterboard);
//        /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
//        return "redirect:/";
//    }
//
//
//
//    @ResponseBody
//    @GetMapping("/images/{filename}")
//    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
//        // sdh1df14-12ee-2353-1593-sd34dfg434.png (filename) - > fileStore.getFullPath(filename)하면
//        // file"/Users/leechanyoung/Downloads/image/communityImageFiles/sdh1df14-12ee-2353-1593-sd34dfg434.png 로 바뀐다.
//        return new UrlResource("file:"+fileDir+filename);
//    }

//    @ResponseBody
//    @GetMapping("test")
//    public Result doTest() {
//
//        WebClient client = WebClient.create("https://dapi.kakao.com/v2/search/web");
//
//        Mono<Result> resultMono = client.get().uri(uriBuilder -> uriBuilder
//                .queryParam("query", "스프링")
//                .build())
//                .header("Authorization", "KakaoAK 0f1c2dc0e54ea9a29fae393f0834cedd")
//                .retrieve()
//                .bodyToMono(Result.class);
//
//        Result result = resultMono.block();
//
//        ArrayList<Documents> documents = result.getDocuments();
//        for (Documents document : documents) {
//            System.out.println("===================================");
//            System.out.println("document = " + document);
//            System.out.println("document.getTitle() = " + document.getTitle());
//            System.out.println("document.getContents() = " + document.getContents());
//            System.out.println("document.getTitle() = " + document.getTitle());
//            System.out.println("document.getUrl() = " + document.getUrl());
//        }
//
//        return result;
//    }
//
//    @ResponseBody
//    @GetMapping("test2")
//    public ResponseEntity<Result> doTest2(){
//
//        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Content-Type", "application/json;charset=UTF-8");
//        headers.set("Authorization", "KakaoAK 0f1c2dc0e54ea9a29fae393f0834cedd");
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
//
//        HttpEntity entity = new HttpEntity("parameters", headers);
//
//        URI url = URI.create("https://dapi.kakao.com/v2/search/web?query=아이언맨");
//        ResponseEntity<Result> response = restTemplate.exchange(url, HttpMethod.GET, entity, Result.class);
//        return response;
//
//    }
}
