package kosta.mvc.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryLineDTO {

	private Long diaryLineId;
	private Long diaryId;
	private Long plannerPlaceId;
	private String diaryLineContent;
	private String diaryLinePhoto;
	private int diaryLinePrice;
	
	private Long placeId;
	private String placeName;
	private String placeAddr;
	private String placeContent;
	private String placePhoto;
	private String placeUrl;
	private int plannerPlaceDate;
	
//	private String diaryTitle;
//	private String diaryType;
//	private int diaryCount;
//	
//	private LocalDate plannerStart;
//	private LocalDate plannerEnd;
}
