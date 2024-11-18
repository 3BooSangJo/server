package org.scoula.common.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Page<T> {
    private int totalCount; // 전체 데이터 건수
    private int totalPage; // 전체 페이지 수
    @JsonIgnore // 프론트엔드 쪽에 json 데이터 전달 안된다
    private PageRequest pageRequest; // 페이지 요청 정보
    private List<T> list; // 데이터 목록

    public static <T> Page of(PageRequest pageRequest, int totalCount, List<T> list) {
//        전체 페이지 수 계산: 전체 데이터 건수/한 페이지당 데이터 개수 -> 올림 처리
        int totalPage = (int)Math.ceil((double)totalCount / pageRequest.getAmount()); // 전체 페이지 수 계산
        return new Page(totalCount, totalPage, pageRequest, list);
    }

    public int getPageNum() { return pageRequest.getPage(); }

    public int getAmount() { return pageRequest.getAmount(); }
}
