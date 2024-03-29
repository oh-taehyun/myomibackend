package com.myomi.comment.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.board.entity.Board;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comments")
@SequenceGenerator(
        name = "COMMENTS_SEQ_GENERATOR",
        sequenceName = "COMMENTS_SEQ",
        initialValue = 1, allocationSize = 1)
public class Comment {
    @Id
    @Column(name = "comment_num", updatable = false)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "COMMENTS_SEQ_GENERATOR")
    private Long commentNum;

    @ManyToOne
    @JoinColumn(name = "board_num", nullable = false,
            updatable = false)
    @JsonIgnore
    private Board board;
    //게시판 글 번호 -> 마이페이지에서 글 정보 필요함
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            updatable = false)
    private User user;

    @Column(name = "content")
    @NotNull
    private String content;


    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "parent", updatable = false)
   // @ManyToOne(fetch = FetchType.LAZY)
    private Long parent;
    //부모 댓글 번호
    
    @JsonIgnore
    @OneToMany(mappedBy="parent",cascade = CascadeType.REMOVE)
    private List<Comment> reply;
  

    @Builder
    public Comment(Long commentNum, Board board, User user, String content, Long parent, LocalDateTime createdDate,
    		List<Comment> reply) {
        this.commentNum = commentNum;
        this.board = board;
        this.user = user;
        this.content = content;
        this.parent = parent;
        this.createdDate = createdDate;
        this.reply =  reply;
    
    }

    public void update(String content) {
        this.content = content;
    }
    
    @PrePersist
    public void prePersist() {
    	 this.parent = this.parent == null ? 0 : this.parent;
    }
}
