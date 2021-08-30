package com.community.domain.entity;

import com.community.domain.dto.board.ReqCommentDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id  @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="comment_id")
    private Long id;

    private String content;

    private LocalDateTime date;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    public void addMember(Member member){
        this.member = member;
        member.getComments().add(this);
    }

    public void addBoard(Board board){
        this.board = board;
        board.getComments().add(this);
    }

    public static Comment createComment(ReqCommentDto reqCommentDto, Member member, Board board){
        Comment comment = new Comment();
        comment.setContent(reqCommentDto.getContent());
        comment.setDate(LocalDateTime.now());
        comment.addMember(member);
        comment.addBoard(board);
        return comment;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    private void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Member getMember() {
        return member;
    }

    private void setMember(Member member) {
        this.member = member;
    }

    public Board getBoard() {
        return board;
    }

    private void setBoard(Board board) {
        this.board = board;
    }
}
