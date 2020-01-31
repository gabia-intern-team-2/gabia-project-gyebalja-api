package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.dto.BoardDto;
import com.gabia.gyebalja.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class BoardApiController {

//    private final BoardService boardService;
//
//    /** 등록 - board 한 건 (게시글 등록) */
//    @PostMapping("/api/v1/boards")
//    public Long postOneBoard(@RequestBody BoardDto boardDto){
//        Long boardId = boardService.save(boardDto);
//
//        return boardId;
//    }
//
//    /** 조회 - board 한 건 (상세페이지) */
//    @GetMapping("/api/v1/boards/{id}")
//    public BoardDto getOneBoard(@PathVariable("id") Long id) {
//        BoardDto boardDto = boardService.findById(id);
//
//        return boardDto;
//    }
//
//    /** 수정 - board 한 건 (상세페이지에서) */
//    @PutMapping("/api/v1/boards/{id}")
//    public Long putOneBoard(@PathVariable("id") Long id, @RequestBody BoardDto boardDto){
//        Long boradId = boardService.update(id, boardDto);
//
//        return boradId;
//    }
//
//    /** 삭제 - board 한 건 (상세페이지에서) */
//    @DeleteMapping("/api/v1/boards/{id}")
//    public Long deleteOneBoard(@PathVariable("id") Long id){
//        boardService.delete(id);
//
//        return 200L; // 검토.
//    }
}

/**
 * 검토 1. try-catch service? controller?
 * 검토 2. 메소드 네이밍 (변수 네이밍)
 * 검토 3. 주석 위치, 주석 방법 등
 * 검토 4. 전체적인 절차 (Controller -> Service -> Repository (domain, dto))
 * 검토 5. 클래스 네이밍 규칙
 * 검토 6. 테스트 클래스의 메소드 이름을 Controller에서 사용된 메소드 이름과 동일하게 함
 */
























//    @GetMapping("/api/v1/boards")
//    public Page<BoardDto> getAllPosts(@PageableDefault()Pageable pageable){
//        Page<Board> page = boardRepository.findAll(pageable);
//        Page<BoardDto> page_map = page.map(board -> new BoardDto(board));
//        return page_map;
//    }