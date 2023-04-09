package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.MusicService;
import com.a603.tonemate.db.entity.*;
import com.a603.tonemate.db.repository.*;
import com.a603.tonemate.dto.common.PitchResult;
import com.a603.tonemate.dto.common.SingerDetail;
import com.a603.tonemate.dto.common.SingerSimilaritytmp;
import com.a603.tonemate.dto.response.*;
import com.a603.tonemate.enumpack.Genre;
import com.a603.tonemate.util.FlaskUtil;
import com.a603.tonemate.util.PitchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final FlaskUtil flaskUtil;
    private final PitchUtil pitchUtil;
    private final TimbreAnalysisRepository timbreAnalysisRepository;
    private final PitchAnalysisRepository pitchAnalysisRepository;
    private final SongRepository songRepository;
    private final SingerRepository singerRepository;
    private final SingerSimilarityRepository singerSimilarityRepository;

    @Transactional
    @Override
    public TimbreAnalysisResp saveTimbreAnalysis(Long userId, MultipartFile file) throws Exception {

        // 플라스크 서버에 분석 처리 요청 후 결과값 받아오기
        Map<String, Object> result = flaskUtil.requestTimbre(file);

        // 분석 결과 데이터 가공하여 응답
        if (result != null) {

            // 유사도 높은 순으로 정렬 (내림차순)
            TreeSet<SingerSimilaritytmp> set = new TreeSet<>((o1, o2) -> Float.compare(o2.getSimilarityPercent(), o1.getSimilarityPercent()));

            List<String> singers = (ArrayList<String>) result.get("singer");
            List<Double> similarityPercents = (ArrayList<Double>) result.get("similaritypercent");
            System.out.println("가수: " + singers);
            System.out.println("유사도: " + similarityPercents.get(0).floatValue());

            //timbre 엔티티 먼저 생성
            TimbreAnalysis timbreAnalysis = TimbreAnalysis.builder()
                    .userId(userId)
                    .mfccMean(((Double) result.get("mfcc_mean")).floatValue())
                    .stftMean(((Double) result.get("stft_mean")).floatValue())
                    .zcrMean(((Double) result.get("zcr_mean")).floatValue())
                    .spcMean(((Double) result.get("spc_mean")).floatValue())
                    .sprMean(((Double) result.get("spr_mean")).floatValue())
                    .rmsMean(((Double) result.get("rms_mean")).floatValue())
                    .mfccVar(((Double) result.get("mfcc_var")).floatValue())
                    .stftVar(((Double) result.get("stft_var")).floatValue())
                    .zcrVar(((Double) result.get("zcr_var")).floatValue())
                    .spcVar(((Double) result.get("spc_var")).floatValue())
                    .sprVar(((Double) result.get("spr_var")).floatValue())
                    .rmsVar(((Double) result.get("rms_var")).floatValue())
                    .build();
            timbreAnalysis = timbreAnalysisRepository.save(timbreAnalysis);
            for (int i = 0; i < singers.size(); i++) {
                String singerName = singers.get(i);
                float similarityPercent = similarityPercents.get(i).floatValue();
                set.add(new SingerSimilaritytmp(singerName, similarityPercent));
            }

            // 상위 5개의 객체 가져오기
            int top = 5;
            int cnt = 0;
            List<SingerSimilaritytmp> singerNames = new ArrayList<>();
            for (SingerSimilaritytmp detail : set) {
                if (cnt == top) {
                    break;
                }
                singerNames.add(detail);
                cnt++;
            }
            List<Singer> singerList = singerRepository.findByNameIn(singerNames.stream().map(SingerSimilaritytmp::getName).collect(Collectors.toList()));
            List<SingerSimilarity> singerSimilarities = new ArrayList<>();
            for (SingerSimilaritytmp singerName : singerNames) {
                for (Singer singer : singerList) {
                    if (singer.getName().equals(singerName.getName())) {
                        singerSimilarities.add(new SingerSimilarity(timbreAnalysis, singer, singerName.getSimilarityPercent()));
                        break;
                    }
                }
            }
            singerSimilarityRepository.saveAll(singerSimilarities);

            // 분석 결과 응답 데이터 생성
            return TimbreAnalysisResp.builder()
                    .mfccMean(timbreAnalysis.getMfccMean())
                    .stftMean(timbreAnalysis.getStftMean())
                    .zcrMean(timbreAnalysis.getZcrMean())
                    .spcMean(timbreAnalysis.getSpcMean())
                    .sprMean(timbreAnalysis.getSprMean())
                    .rmsMean(timbreAnalysis.getRmsMean())
                    .mfccVar(timbreAnalysis.getMfccVar())
                    .stftVar(timbreAnalysis.getStftVar())
                    .zcrVar(timbreAnalysis.getZcrVar())
                    .spcVar(timbreAnalysis.getSpcVar())
                    .sprVar(timbreAnalysis.getSprVar())
                    .rmsVar(timbreAnalysis.getRmsVar())
                    .timbreId(timbreAnalysis.getTimbreId())
                    .time(timbreAnalysis.getTime())
                    .singerDetails(singerSimilarities.stream().map(o -> new SingerDetail(o.getSinger().getName(), o.getSimilarityPercent(), o.getSinger().getSongs().stream().limit(5).map(this::toSongResp).collect(Collectors.toList()))).collect(Collectors.toList()))
                    .build();
        }
        return null;
    }

    @Override
    public ResultResp getResultList(Long userId) {

        List<PitchAnalysis> pitchAnalysisList = pitchAnalysisRepository.findAllByUserIdOrderByPitchIdDesc(userId);
        List<TimbreAnalysis> timbreAnalysisList = timbreAnalysisRepository.findAllByUserIdOrderByTimbreIdDesc(userId);

        List<TimbreResultResp> timbreResultRespList = timbreAnalysisList
                .stream()
                .map(x -> TimbreResultResp.builder()
                        .timbreId(x.getTimbreId())
                        .time(x.getTime())
                        .singer(x.getSingerSimilarities().stream()
                                .map(o -> o.getSinger().getName())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
        List<PitchResultResp> pitchResultRespList = pitchAnalysisList
                .stream()
                .map(x -> PitchResultResp.builder()
                        .pitchId(x.getPitchId())
                        .time(x.getTime())
                        .lowOctave(pitchUtil.getOctaveName(x.getOctaveLow()))
                        .highOctave(pitchUtil.getOctaveName(x.getOctaveHigh()))
                        .build())
                .collect(Collectors.toList());

        return ResultResp.builder()
                .pitch(pitchResultRespList)
                .timbre(timbreResultRespList)
                .build();
    }

    @Transactional
    @Override
    public TimbreAnalysisResp selectOneTimbreAnalysis(Long userId, Long timbreId) {

        TimbreAnalysis timbreAnalysis = timbreAnalysisRepository.findByTimbreIdAndUserId(timbreId, userId).orElseThrow();
        System.out.println(timbreAnalysis.getSingerSimilarities().get(0).getSinger().getSongs().size());
        return TimbreAnalysisResp.builder()
                .timbreId(timbreAnalysis.getTimbreId())
                .mfccMean(timbreAnalysis.getMfccMean())
                .stftMean(timbreAnalysis.getStftMean())
                .zcrMean(timbreAnalysis.getZcrMean())
                .spcMean(timbreAnalysis.getSpcMean())
                .sprMean(timbreAnalysis.getSprMean())
                .rmsMean(timbreAnalysis.getRmsMean())
                .mfccVar(timbreAnalysis.getMfccVar())
                .stftVar(timbreAnalysis.getStftVar())
                .zcrVar(timbreAnalysis.getZcrVar())
                .spcVar(timbreAnalysis.getSpcVar())
                .sprVar(timbreAnalysis.getSprVar())
                .rmsVar(timbreAnalysis.getRmsVar())
                .time(timbreAnalysis.getTime())
                .singerDetails(timbreAnalysis.getSingerSimilarities().stream().map(o -> new SingerDetail(
                                o.getSinger().getName(),
                                o.getSimilarityPercent(),
                                o.getSinger().getSongs().stream()
                                        .limit(5)
                                        .map(this::toSongResp)
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public PitchAnalysisResp selectOnePitchAnalysis(Long userId, Long pitchId) {
        PitchAnalysis pitchAnalysis = pitchAnalysisRepository.findByPitchIdAndUserId(pitchId, userId).orElseThrow();

        List<Long> possibleList = convertStringToLongList(pitchAnalysis.getPossibleList());
        List<Long> normalList = convertStringToLongList(pitchAnalysis.getNormalList());
        List<Long> impossibleList = convertStringToLongList(pitchAnalysis.getImpossibleList());

        List<SongPitchResp> possibleSong = songRepository.findBySongIdIn(possibleList).stream()
                .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                .collect(Collectors.toList());
        List<SongPitchResp> normalSong = songRepository.findBySongIdIn(normalList).stream()
                .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                .collect(Collectors.toList());
        List<SongPitchResp> impossibleSong = songRepository.findBySongIdIn(impossibleList).stream()
                .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                .collect(Collectors.toList());

        return PitchAnalysisResp.builder()
                .lowOctave(pitchUtil.getOctaveName(pitchAnalysis.getOctaveLow()))
                .highOctave(pitchUtil.getOctaveName(pitchAnalysis.getOctaveHigh()))
                .possibleSong(possibleSong)
                .normalSong(normalSong)
                .impossibleSong(impossibleSong)
                .pitchId(pitchAnalysis.getPitchId()).time(pitchAnalysis.getTime()).build();
    }

    @Override
    public PitchAnalysisResp analysisPitch(Long userId, MultipartFile highOctave, MultipartFile lowOctave) {
        System.out.println(highOctave + " " + lowOctave);
        PitchResult lowPitch = pitchUtil.getPitch(lowOctave, false);
        PitchResult highPitch = pitchUtil.getPitch(highOctave, true);
        System.out.println("low and high : " + lowPitch + ", " + highPitch);
        // 복구전까지 임시처리
//        int randLow = new Random().nextInt(28);
//        int randHigh = new Random().nextInt(27) + 32;

        List<Song> possibleNormalSong = songRepository.findByOctaveInRange(lowPitch.getPitch(), highPitch.getPitch(), PageRequest.of(0, 6));

        int size = possibleNormalSong.size();
        List<Song> possibleSong = possibleNormalSong.subList(0, Math.min(3, size));

        List<Song> normalSong = new ArrayList<>();
        if (size > 3) {
            normalSong = possibleNormalSong.subList(3, Math.min(6, size));
        }

        List<Song> impossibleSong = songRepository.findByOctaveOverlap(lowPitch.getPitch(), highPitch.getPitch(), PageRequest.of(0, 3));

        List<Long> possibleSongId = new ArrayList<>();
        List<Long> normalSongId = new ArrayList<>();
        List<Long> impossibleSongId = new ArrayList<>();

        //50 이하의 데이터에서 for문과 큰 성능 차이가 없다. forEach가 더 유연한 대처가능
        int possibleNormalSongLen = Math.min(3, possibleNormalSong.size());

        possibleNormalSong.subList(0, possibleNormalSongLen).forEach(song -> possibleSongId.add(song.getSongId()));
        possibleNormalSong.subList(possibleNormalSongLen, Math.min(possibleNormalSongLen + 3, possibleNormalSong.size())).forEach(song -> normalSongId.add(song.getSongId()));
        impossibleSong.forEach(song -> impossibleSongId.add(song.getSongId()));

        PitchAnalysis pitchAnalysis = pitchAnalysisRepository.save(PitchAnalysis.builder().octaveLow(lowPitch.getPitch())
                .octaveHigh(highPitch.getPitch()).userId(userId).possibleList(possibleSongId.toString())
                .normalList(normalSongId.toString()).impossibleList(impossibleSongId.toString()).build());

        return PitchAnalysisResp.builder().lowOctave(pitchUtil.getOctaveName(lowPitch.getPitch())).highOctave(pitchUtil.getOctaveName(highPitch.getPitch()))
                .possibleSong(possibleSong.stream()
                        .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                        .collect(Collectors.toList()))
                .normalSong(normalSong.stream()
                        .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                        .collect(Collectors.toList()))
                .impossibleSong(impossibleSong.stream()
                        .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                        .collect(Collectors.toList()))
                .pitchId(pitchAnalysis.getPitchId())
                .time(pitchAnalysis.getTime()).build();
    }


    @Override
    public PitchAnalysisResp analysisPitchByGenre(Long userId, String genre, Long pitchId) {
        Genre genreEnum = Genre.fromCode(genre);

        PitchAnalysis pitchAnalysis = pitchAnalysisRepository.findByPitchIdAndUserId(pitchId, userId).orElseThrow();

        List<Long> possibleList = convertStringToLongList(pitchAnalysis.getPossibleList());
        List<Long> normalList = convertStringToLongList(pitchAnalysis.getNormalList());
        List<Long> impossibleList = convertStringToLongList(pitchAnalysis.getImpossibleList());

        List<SongPitchResp> possibleSongs = songRepository.findSingerByIdAndGenre(possibleList, genreEnum).stream()
                .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                .collect(Collectors.toList());
        List<SongPitchResp> normalSongs = songRepository.findSingerByIdAndGenre(normalList, genreEnum).stream()
                .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                .collect(Collectors.toList());
        List<SongPitchResp> impossibleSongs = songRepository.findSingerByIdAndGenre(impossibleList, genreEnum).stream()
                .map((Song song) -> toSongPitchResp(song, pitchUtil.getOctaveName(song.getOctaveLow()), pitchUtil.getOctaveName(song.getOctaveHigh())))
                .collect(Collectors.toList());

        return PitchAnalysisResp.builder()
                .possibleSong(possibleSongs)
                .normalSong(normalSongs)
                .impossibleSong(impossibleSongs)
                .build();
    }

    @Override
    @Transactional
    public void deleteTimbreResult(Long userId, Long timbreId) {
        timbreAnalysisRepository.deleteByTimbreIdAndUserId(timbreId, userId);
    }

    @Override
    @Transactional
    public void deletePitchResult(Long userId, Long pitchId) {
        pitchAnalysisRepository.deleteByPitchIdAndUserId(pitchId, userId);
    }

    // 문자열 배열을 실제 리스트로 생성 str ="[1, 2, 3, 4]"
    private List<Long> convertStringToLongList(String str) {
        List<Long> convertList = new ArrayList<>();
        if ("[]".equals(str)) return convertList;

        String[] stringArray = str.substring(1, str.length() - 1).split(",");
        for (String s : stringArray) {
            String trimmed = s.trim();
            Long converted = Long.valueOf(trimmed);
            convertList.add(converted);
        }
        return convertList;
    }

}
