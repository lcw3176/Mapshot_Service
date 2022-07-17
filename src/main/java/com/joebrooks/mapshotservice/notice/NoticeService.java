package com.joebrooks.mapshotservice.notice;

import com.joebrooks.mapshotservice.admin.notice.edit.AdminEditNoticeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void addPost(NoticeEntity noticeEntity){
        noticeRepository.save(noticeEntity);
    }

    public Page<NoticeEntity> getPosts(int index){
        return noticeRepository.findAll(PageRequest.of(index, 10, Sort.by("id").descending()));
    }

    public Optional<NoticeEntity> getPost(long id){
        return noticeRepository.findById(id);
    }

    public void removePost(long id){
        noticeRepository.deleteById(id);
    }

    public void editPost(AdminEditNoticeRequest noticeRequest){

        NoticeEntity noticeEntity = noticeRepository.findById(noticeRequest.getId())
                .orElseThrow(() -> {
                    throw new RuntimeException("no such post");
                });

        noticeEntity.changeNoticeType(noticeRequest.getNoticeType());
        noticeEntity.changeContent(noticeRequest.getContent());
        noticeEntity.changeTitle(noticeRequest.getTitle());
    }

    public long getSize(){
        return noticeRepository.count();
    }
}
