package kosta.mvc.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "goods")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goods_seq")
	@SequenceGenerator(sequenceName = "goods_seq", allocationSize = 1, name = "goods_seq")
	private Long goodsId; //상품번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_fk")
	@JsonIgnore
	private Place place; //장소데이터 번호
	
	//마이리얼트립 참고>> 애월, 제주시, 함덕/구좌, 성산/우도, 표선, 서귀포, 중문, 한림/협재
	private String goodsLocalCategory; //지역카테고리
	private String goodsCategory; //액티비티, 입장권, 스파/힐링, 대여 
	private String goodsName;
	private int goodsPrice;
	
	@Column(length = 2000)
	private String goodsContent;
	
	private String goodsPhoto;
	private String goodsAddr;
	
	@Transient
	private MultipartFile file;
	
	/** 상품후기 */
	@OneToMany(mappedBy = "goods")
	private List<GoodsReply> goodsReplyList;
	
	/** 상품상세 */
	//@OneToMany(mappedBy = "goods")
	//private List<GoodsLine> goodsLineList;

}
