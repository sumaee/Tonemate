package com.a603.tonemate.dto.request;

import com.a603.tonemate.db.entity.Song;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@ApiModel(value = "음역대 분석 요청 데이터", description = "사용자가 보낸 wav 파일에 대한 음색 분석 결과 정보들이 담김")
public class PitchAnalysisReq {
	
	@ApiModelProperty(value = "높은 음을 찾을 때 사용할 파일")
	@NotNull(message = "높은 음 파일이 필요합니다.")
	private MultipartFile highOctave;
	
	@ApiModelProperty(value = "낮은 음을 찾을 때 사용할 파일")
	@NotNull(message = "낮은음 파일이 필요합니다.")
	private MultipartFile lowOctave;

}
