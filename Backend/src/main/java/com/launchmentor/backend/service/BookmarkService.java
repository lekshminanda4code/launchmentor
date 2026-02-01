package com.launchmentor.backend.service;

import com.launchmentor.backend.entity.Bookmark;
import com.launchmentor.backend.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    private final BookmarkRepository repo;

    public BookmarkService(BookmarkRepository repo) {
        this.repo = repo;
    }

    public Bookmark addBookmark(Long userId, Long careerId) {
        Bookmark b = new Bookmark(userId, careerId);
        return repo.save(b);
    }

    public List<Bookmark> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public void removeBookmark(Long userId, Long careerId) {
        repo.deleteByUserIdAndCareerId(userId, careerId);
    }
    public Optional<Bookmark> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    
}
