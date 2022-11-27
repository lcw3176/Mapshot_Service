package com.joebrooks.mapshotservice.user.domain.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private static final int PAGE_SIZE = 10;

    public void addPost(NoticeEntity noticeEntity){
        noticeRepository.save(noticeEntity);
    }

    public Page<NoticeEntity> getPosts(int index){
        return noticeRepository.findAll(PageRequest.of(index, PAGE_SIZE, Sort.by("id").descending()));
    }

    public NoticeEntity getPost(long id){
        return noticeRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("잘못된 공지사항 접근");
        });
    }

    public void removePost(long id){
        noticeRepository.deleteById(id);
    }

    public void editPost(NoticeEntity noticeEntityToChange){

        NoticeEntity noticeEntity = noticeRepository.findById(noticeEntityToChange.getId())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("해당되는 공지사항 없음");
                });

        noticeEntity.changeNoticeType(noticeEntityToChange.getNoticeType());
        noticeEntity.changeContent(noticeEntityToChange.getContent());
        noticeEntity.changeTitle(noticeEntityToChange.getTitle());
    }
}
