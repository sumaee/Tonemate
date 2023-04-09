package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.LikeSong;
import com.a603.tonemate.db.repository.custom.LikeSongCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeSongRepository extends JpaRepository<LikeSong, Long>, LikeSongCustomRepository {
    Page<LikeSong> findByUserId(Long userId, Pageable pageable);
}
