package org.scoula.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.service.BoardService;
import org.scoula.common.pagination.Page;
import org.scoula.common.pagination.PageRequest;
import org.scoula.common.util.UploadFiles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController // 모든 메소드에 @ResponseBody 추가해줌 -> 응답을 json으로 받아온다
@RequestMapping("/api/board") // 메소드들의 공통 url
@RequiredArgsConstructor // final이 붙은 필드로 생성자 만들어줌
@Slf4j // 로깅을 위한 어노테이션
public class BoardController {
    private final BoardService service;

    //    http://localhost:8080/api/board
//    전체 게시글 조회
//    @GetMapping()
//    public ResponseEntity<List<BoardDTO>> getList()
//    {
////        상태 코드가 200이고 body 타입이 List<BoardDTO>인 응답 객체를 리턴
//        return ResponseEntity.ok(service.getList());
//    }

    @GetMapping("")
    public ResponseEntity<Page> getList(PageRequest pageRequest) {
        return ResponseEntity.ok(service.getPage(pageRequest));
    }

    //    http://localhost:8080/api/board/7
//    특정 게시글 조회
    @GetMapping("/{no}")
    public ResponseEntity<BoardDTO> get(@PathVariable Long no){
//        상태 코드가 200이고 body 타입이 BoardDTO인 응답 객체를 리턴
        return ResponseEntity.ok(service.get(no));
    }

    //    http://localhost:8080/api/board
//    새 게시글 생성
    @PostMapping()
    public ResponseEntity<BoardDTO> create(BoardDTO board){
//        파일 업로드를 하기 위해서는 @RequestBody를 붙이면 안된다
        return ResponseEntity.ok(service.create(board));
    }

    //    http://localhost:8080/api/board/14
//    기존 게시글 수정
    @PutMapping("/{no}")
    public ResponseEntity<BoardDTO> update(@PathVariable Long no, BoardDTO board){
        return ResponseEntity.ok(service.update(board));
    }

    //    http://localhost:8080/api/board/14
//    기존 게시글 삭제
    @DeleteMapping("/{no}")
    public ResponseEntity<BoardDTO> delete(@PathVariable Long no){
        return ResponseEntity.ok(service.delete(no));
    }

    //    http://localhost:8080/api/board/download/10
//    해당하는 경로로 해당 첨부파일 다운로드
    @GetMapping("/download/{no}")
    public void download(@PathVariable Long no, HttpServletResponse response) throws IOException {
        BoardAttachmentVO attachment = service.getAttachment(no);
        File file = new File(attachment.getPath());
        UploadFiles.download(response,file,attachment.getFilename());
    }

    //    http://localhost:8080/api/board/deleteAttachment/10
//    해당 첨부파일 삭제
    @DeleteMapping("/deleteAttachment/{no}")
    public ResponseEntity<Boolean> deleteAttachment(@PathVariable Long no){
        return ResponseEntity.ok(service.deleteAttachment(no));
    }


}


