package com.joebrooks.mapshotservice.user.notice;

import com.joebrooks.mapshotservice.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "notice")
public class NoticeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

    @Column(name = "content", length = 2000)
    private String content;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeNoticeType(NoticeType noticeType){
        this.noticeType = noticeType;
    }

    public void changeContent(String content){
        this.content = content;
    }

}
