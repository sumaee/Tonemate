package com.a603.tonemate.db.repository.custom;

import com.a603.tonemate.dto.common.KaraokeTopDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.a603.tonemate.db.entity.QKaraokeTop.karaokeTop;
import static com.a603.tonemate.db.entity.QLikeSong.likeSong;

@Repository
@RequiredArgsConstructor
public class KaraokeTopCustomRepositoryImpl implements KaraokeTopCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<KaraokeTopDto> findAll(Long userId, Pageable pageable) {
        List<KaraokeTopDto> songs = query
                .select(Projections.constructor(KaraokeTopDto.class,
                        karaokeTop.karaokeTopId,
                        karaokeTop.karaoke.tjNum,
                        karaokeTop.karaoke.singer,
                        karaokeTop.karaoke.title,
                        new CaseBuilder()
                                .when(likeSong.userId.eq(userId))
                                .then(true)
                                .otherwise(false)
                ))
                .from(karaokeTop)
                .leftJoin(likeSong)
                .on(likeSong.karaoke.eq(karaokeTop.karaoke), likeSong.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> songSize = query
                .select(karaokeTop.count())
                .from(karaokeTop);

        return PageableExecutionUtils.getPage(songs, pageable, songSize::fetchOne);
    }
}
