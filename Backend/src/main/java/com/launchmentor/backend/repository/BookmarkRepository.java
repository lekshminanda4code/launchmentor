package com.launchmentor.backend.repository;

import com.launchmentor.backend.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserId(Long userId);
    List<Bookmark> findByCareerId(Long careerId);
    void deleteByUserIdAndCareerId(Long userId, Long careerId);
}
