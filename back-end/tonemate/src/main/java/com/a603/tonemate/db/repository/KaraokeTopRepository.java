package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.KaraokeTop;
import com.a603.tonemate.db.repository.custom.KaraokeTopCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KaraokeTopRepository extends JpaRepository<KaraokeTop, Long>, KaraokeTopCustomRepository {

}
