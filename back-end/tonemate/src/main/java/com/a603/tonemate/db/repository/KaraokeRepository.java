package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.Karaoke;
import com.a603.tonemate.db.repository.custom.KaraokeCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KaraokeRepository extends JpaRepository<Karaoke, Long>, KaraokeCustomRepository {

    Optional<Karaoke> findByTjNum(Integer tjNum);

}
