package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.dto.board.BoardRequestDto;
import com.gabia.gyebalja.dto.board.BoardResponseDto;
import com.gabia.gyebalja.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    /** 등록 - board 한 건 (게시글 등록) */
    @PostMapping("/api/v1/boards")
    public Long postOneBoard(@RequestBody BoardRequestDto boardRequestDto){
        Long boardId = boardService.postOneBoard(boardRequestDto);

        return boardId;
    }

    /** 조회 - board 한 건 (상세페이지) */
    @GetMapping("/api/v1/boards/{id}")
    public BoardResponseDto getOneBoard(@PathVariable("id") Long id) {
        BoardResponseDto boardResponseDto = boardService.getOneBoard(id);

        return boardResponseDto;
    }

    /** 수정 - board 한 건 (상세페이지에서) */
    @PutMapping("/api/v1/boards/{id}")
    public Long putOneBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto){
        Long boradId = boardService.putOneBoard(id, boardRequestDto);

        return boradId;
    }

    /** 삭제 - board 한 건 (상세페이지에서) */
    @DeleteMapping("/api/v1/boards/{id}")
    public Long deleteOneBoard(@PathVariable("id") Long id){
        boardService.deleteOneBoard(id);

        return 200L; // 검토.
    }

    /** 조회 - board 전체 (페이징) */
    @GetMapping("/api/v1/boards")
    public Page<BoardResponseDto> getAllBoard(@PageableDefault(size=10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        // Example - http://localhost:8080/api/v1/boards?page=0&size=4&sort=id,desc
        Page<BoardResponseDto> boardDtoPage = boardService.getAllBoard(pageable);

        return boardDtoPage;
    }

//    /** 조회 - board 전체 (페이징) */
//    @GetMapping("/api/v1/boards")
//    public Result getAllBoard(@PageableDefault(size=10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
//        Page<BoardResponseDto> boardResponseDtosPage = boardService.getAllBoard(pageable);
//        List<BoardResponseDto> boardRequestDtos = boardResponseDtosPage.getContent();
//        Pageable pageInfo = boardResponseDtosPage.getPageable();
//
//        return new Result(code, message, result);
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private T code;
//        private T message;
//        private T result;
//    }
}
