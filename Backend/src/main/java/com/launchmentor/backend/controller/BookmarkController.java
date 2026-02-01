package com.launchmentor.backend.controller;

import com.launchmentor.backend.entity.Bookmark;
import com.launchmentor.backend.service.BookmarkService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@CrossOrigin(origins = "*")
public class BookmarkController {

    private final BookmarkService service;

    public BookmarkController(BookmarkService service) {
        this.service = service;
    }

    // ------------------ CREATE BOOKMARK ------------------
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        Long careerId = body.get("careerId");

        if (userId == null || careerId == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "userId and careerId are required"));
        }

        Bookmark b = service.addBookmark(userId, careerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(b);
    }

    // ------------------ GET ALL BOOKMARKS FOR A USER ------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable Long userId) {
        List<Bookmark> list = service.getByUser(userId);
        return ResponseEntity.ok(list);
    }

    // ------------------ GET BOOKMARK BY ID ------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Bookmark> opt = service.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Bookmark not found"));
        }
    }

    // ------------------ DELETE BOOKMARK BY ID ------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }
}


