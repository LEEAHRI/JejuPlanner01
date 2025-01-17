package kosta.mvc.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "planboard")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planboard_seq")
	@SequenceGenerator(sequenceName = "planboard_seq", allocationSize = 1, name = "planboard_seq")
	private Long pboardId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userplan_fk")
	@JsonIgnore
	private Planner userPlan; //사용자플래너번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_fk")
	@JsonIgnore
	private Users user;
	
	private String pboardCategory;
	private String pboardTitle;
	
	@Column(length = 2000)
	private String pboardContent;
	private String pboardAttach;
	
	@CreationTimestamp
	private LocalDateTime pboardRegdate;
		
	private int likesCount;
	
	@Transient
	private MultipartFile file;
	
	@Transient
	private boolean isChecked;
	
			
	/**댓글*/
	@OneToMany(mappedBy = "planBoard", cascade = CascadeType.ALL)
	private List<PlanReply> planReply;
	
	/**좋아요*/
	@OneToMany(mappedBy = "planBoard" , cascade = CascadeType.ALL)
	Set<Likes> likes = new HashSet<>();

}
