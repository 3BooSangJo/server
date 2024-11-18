package org.scoula.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.common.util.UploadFiles;
import org.scoula.member.dto.ChangePasswordDTO;
import org.scoula.member.dto.MemberDTO;
import org.scoula.member.dto.MemberJoinDTO;
import org.scoula.member.dto.MemberUpdateDTO;
import org.scoula.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    final MemberService service;

//    @PathVariable : 경로상에 username을 변수 처리(이름 일치해야함)
    @GetMapping("/checkusername/{username}")
    public ResponseEntity<Boolean> checkUsername(@PathVariable String username) {
//        주어진 사용자 이름의 중복 여부 체크하고 결과를 ResponseEntity로 반환
        return ResponseEntity.ok().body(service.checkDuplicate(username));
    }

    @PostMapping("")
    public ResponseEntity<MemberDTO> join(MemberJoinDTO member) {
//        회원 가입 처리하고 가입된 회원 정보를 ResponseEntity로 반환
        return ResponseEntity.ok(service.join(member));
    }

//    특정 사용자의 아바타 이미지를 가져오는 메소드
    @GetMapping("/{username}/avatar")
    public void getAvatar(@PathVariable String username, HttpServletResponse response) {
        String avatarPath = "/Users/ldh/upload/avatar/" + username + ".png"; // 이미지 경로 생성

        System.out.printf(avatarPath);

        File file = new File(avatarPath);

        System.out.printf(file.exists() ? "asd" : "null");
//        아바타 이미지가 존재하지 않을 경우, 기본 아바타 이미지 사용
        if(!file.exists()) { // 아바타 등록이 없는 경우, 디폴트 아바타 이미지 사용
            file = new File("/Users/ldh/upload/avatar/unknown.png");
        }
        UploadFiles.downloadImage(response, file);
    }

//    회원 프로필 변경 요청을 처리하는 메소드
    @PutMapping("/{username}")
    public ResponseEntity<MemberDTO> changeProfile(MemberUpdateDTO member) {
//        객체를 통째로 받아서 그 안의 username을 경로 변수로 넘겨준다
        return ResponseEntity.ok(service.update(member));
    }

    @PutMapping("/{username}/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        service.changePassword(changePasswordDTO);
        return ResponseEntity.ok().build();
    }
}
