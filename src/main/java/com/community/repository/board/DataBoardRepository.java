package com.community.repository.board;

import com.community.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DataBoardRepository extends JpaRepository<Board, Long> {

    // By 뒷 부분은 SQL의 where 조건 절에 해당. Containing을 붙여주면 Like 검색이 된다.
    List<Board> findByTitleContaining(String keyword);

    List<Board> findByContentContaining(String keyword);


}
