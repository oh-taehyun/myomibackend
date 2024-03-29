package com.myomi.board.control;

import java.io.IOException;
import java.lang.module.FindException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.dto.PageBean;
import com.myomi.board.service.BoardService;
import com.myomi.common.status.AddException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
//@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "게시판")
public class BoardController {
    private final BoardService service;
    
    @ApiOperation(value = "게시판 | 글 목록 보기 ")
    @GetMapping("/board/list")
    public ResponseEntity<?> boardAll(@RequestParam(required= false, defaultValue="1") int currentPage) throws FindException {
    	PageBean<BoardReadResponseDto> pb = service.getBoard(currentPage);
		return new ResponseEntity<>(pb,HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 | 제목으로 검색")
    @GetMapping("/board/list/{keyword}")
    public ResponseEntity<?> boardAllByTitle(@PathVariable String keyword,
                                             @PageableDefault(sort = {"createdDate"}, direction = Direction.DESC) Pageable pageable) throws FindException {
        List<BoardReadResponseDto> list = service.getByTitle(keyword, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 | 제목 + 카테고리로 검색 ")
    @GetMapping("/board/list/{category}/{title}")
    public ResponseEntity<?> boardAllByTitleAndCategory(@PathVariable String category,
                                                        @PathVariable String title,
                                                        @RequestParam(required= false, defaultValue="1") int currentPage) throws FindException {
    	PageBean<BoardReadResponseDto> pb = service.getByCategoryAndTitle(currentPage, category, title);
        return new ResponseEntity<>(pb, HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 | 글 작성 ")
    @PostMapping("/board/add")  //로그인필요
    public ResponseEntity<?> boardAdd(String category, String title, String content,
                                      MultipartFile file,
                                      Authentication user) throws AddException, IOException {
        BoardReadResponseDto dto = BoardReadResponseDto.builder()
                .content(content)
                .title(title)
                .category(category)
                .file(file)
                .build();
        service.addBoard(dto, user);
//		log.info("컨트롤러:" + file.getName() + "사이즈는: "+ file.getSize());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 | 글 상세 + 댓글 보기 ")
    @GetMapping("/board/detail/{boardNum}")
    public ResponseEntity<?> boardDetail(@PathVariable Long boardNum, Authentication user) {
        BoardReadResponseDto dto = service.detailBoard(boardNum, user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 | 글 수정 ")
    @PutMapping("/board/{boardNum}") //로그인필요
    public ResponseEntity<?> boardModify(String category, String title, String content,
                                         MultipartFile file,
                                         @PathVariable Long boardNum, Authentication user) throws AddException, IOException {
        BoardReadResponseDto dto = BoardReadResponseDto.builder()
                .content(content)
                .title(title)
                .category(category)
                .file(file)
                .build();
        service.modifyBoard(dto, boardNum, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 | 글 삭제 ")
    @DeleteMapping("/board/{boardNum}") //로그인필요
    public ResponseEntity<?> boardDelete(@PathVariable Long boardNum, Authentication user){
        service.deleteBoard(boardNum, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지 | 내가 작성한 글 보기 ")
    @GetMapping("/mypage/boardList")
    public ResponseEntity<?> myBoardList(Authentication user) throws FindException {
        List<BoardReadResponseDto> list = service.findBoardListByUser(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}