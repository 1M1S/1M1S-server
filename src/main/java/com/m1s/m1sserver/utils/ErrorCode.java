package com.m1s.m1sserver.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    SAVE_FAILED(HttpStatus.NOT_ACCEPTABLE, "저장에 실패했습니다."),
    NO_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "로그아웃 된 사용자입니다"),
    PARTICIPANT_NOT_FOUND(HttpStatus.NOT_FOUND, "그룹에 참여중이지 않습니다."),
    MEMBER_COUNSEL_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 상담 결과가 없습니다."),
    COUNSEL_SOLUTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 솔루션이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    RANK_NOT_FOUND(HttpStatus.NOT_FOUND, "랭킹 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    CURRICULUM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 커리큘럼을 찾을 수 없습니다."),
    MEMBER_CURRICULUM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 멤버 커리큘럼을 찾을 수 없습니다."),
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."),
    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 아이디입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    DUPLICATE_PHONE(HttpStatus.CONFLICT, "중복된 연락처입니다."),
    DUPLICATE_PARTICIPANT(HttpStatus.CONFLICT, "이미 참여중인 회원입니다."),
    DUPLICATE_PASSWORD(HttpStatus.CONFLICT, "기존 비밀번호와 동일합니다.")

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}