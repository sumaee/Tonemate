package com.a603.tonemate.db.repository.custom;

import com.a603.tonemate.dto.common.KaraokeTopDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KaraokeTopCustomRepository {
    Page<KaraokeTopDto> findAll(Long userId, Pageable pageable);
}
