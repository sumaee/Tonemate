package com.a603.tonemate.db.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.a603.tonemate.db.entity.QLikeSong.likeSong;

@Repository
@RequiredArgsConstructor
public class LikeSongCustomRepositoryImpl implements LikeSongCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public void delete(Long userId, Integer tjNum) {
        query.delete(likeSong)
                .where(likeSong.userId.eq(userId), likeSong.karaoke.tjNum.eq(tjNum))
                .execute();
    }
}
