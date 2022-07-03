package com.joebrooks.mapshotservice.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private long noticeSize;

    @PostConstruct
    private void init(){
        fetchSize();
    }

    private void fetchSize(){
        noticeSize = noticeRepository.count();
    }

    public void addPost(NoticeEntity noticeEntity){
        noticeRepository.save(noticeEntity);
        fetchSize();
    }

    public Page<NoticeEntity> getPosts(int index){
        return noticeRepository.findAll(PageRequest.of(index, 10, Sort.by("id").descending()));
    }

    public Optional<NoticeEntity> getPost(long id){
        return noticeRepository.findById(id);
    }

    public void removePost(long id){
        noticeRepository.deleteById(id);
        fetchSize();
    }

    public void editPost(NoticeEntity noticeEntity){
        noticeRepository.save(noticeEntity);
    }

    public long getSize(){
        return noticeSize;
    }
}
