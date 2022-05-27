package kosta.mvc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "place")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class Place {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_seq")
	@SequenceGenerator(sequenceName = "place_seq", allocationSize = 1, name = "place_seq")
	@NonNull
	private Long placeId; //장소데이터 번호
	
	private String placeCategory;
	private String placeName;
	private String placeAddr;
	
	@Column(length = 2000)
	private String placeContent;
	private String placePhoto;
	private String placeUrl;
	private String placeLatitude; //위도
	private String placeLongitude; //경도
	private int placeSave; //담은 개수
}
