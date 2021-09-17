package com.community.repository.board;

import com.community.domain.dto.board.BoardDto;
import com.community.domain.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {
    private static final int BLOCK_PAGE_NUM_COUNT = 5;
    private static final int PAGE_POST_COUNT = 7;
    private final EntityManager em;

    public List<BoardDto> findAllByPaging(Integer pageNum){
        List<Board> boards = em.createQuery(
                "select distinct b from Board b" +
                        " join fetch b.member m order by b.date desc", Board.class)
                .setFirstResult((pageNum-1)*PAGE_POST_COUNT)
                .setMaxResults(PAGE_POST_COUNT)
                .getResultList();
        for (Board board : boards) {
            BoardDto.createboardDto(board);
        }
        return boards.stream().map(BoardDto::createboardDto).collect(toList());
    }
}
