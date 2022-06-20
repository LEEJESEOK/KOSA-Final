
/*************************************************************
 파일명: BoardController.java
 기능: 게시판 글 조회/작성/수정/삭제
 작성자: 진영서
 [코멘트: RestController, 페이징 처리를 위한 Pager 클래스 활용]
 *************************************************************/

        package com.hyundai.kosafinal.controller;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import javax.servlet.http.HttpServletResponse;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.util.FileCopyUtils;
        import org.springframework.web.bind.annotation.DeleteMapping;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.multipart.MultipartFile;

        import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j

public class BoardController {
    @GetMapping("/board")
    public void board() {
        log.info("보드");
    }



}
