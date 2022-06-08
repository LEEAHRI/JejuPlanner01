package kosta.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;


import kosta.mvc.domain.Place;
import kosta.mvc.domain.Planner;
import kosta.mvc.domain.PlannerPlace;
//import kosta.mvc.domain.QPlanner;
import kosta.mvc.domain.Users;
import kosta.mvc.dto.PlannerPlaceDTO;
import kosta.mvc.repository.PlaceRepository;
import kosta.mvc.repository.PlannerPlaceRepository;
import kosta.mvc.repository.PlannerRepository;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {

	private final PlannerRepository plannerRep;
	private final PlannerPlaceRepository plannerPlaceRep;
	private final PlaceRepository placeRep;
	
	private final static String DIARY_DEFAULT_NAME = "제주도";
	
	
	@Override
	public List<Planner> selectAll(String userId) {
			List<Planner> plist = plannerRep.selectByUserID(userId);
			
			return plist;
	}

	/*@Override
	public PlannerDTO selectBy(Long plannerId) {
		Planner planner = plannerRep.findById(plannerId).orElse(null);
		System.out.println("planner = "+ planner);
		System.out.println("------------------------");
		if(planner==null) throw new RuntimeException("해당 플래너 정보가 없습니다.");
		Users user =  planner.getUser();
		System.out.println(user);
		
		UserDTO userDto = new UserDTO(user.getUserId(), user.getUserName(), user.getUserPassword(), user.getUserPhone());
		PlannerDTO dto=new PlannerDTO(planner.getPlannerId(), userDto,  planner.getPlannerName(), planner.getPlannerType(), planner.getPlannerCount());
		
		return dto;
	}*/
	
	@Override
	public Planner selectBy(Long plannerId) {
		Planner planner = plannerRep.findById(plannerId).orElse(null);
		System.out.println("planner = "+ planner);
		System.out.println("------------------------");
		if(planner==null) throw new RuntimeException("해당 플래너 정보가 없습니다.");
		
		return planner;
	}

	@Override//일자별 플래너 검색
	public Planner selectByDay(Long plannerId, int day) {
		
		
		
		
		return null;
	}
	
	/**플래너 상세 일정 조회*/
	@Override
	public List<PlannerPlaceDTO> selectPlaceByPlanner(Long plannerId) {
		
		Planner dbplanner =plannerRep.findById(plannerId)
				.orElseThrow(()-> new RuntimeException("존재하지 않는 플래너입니다."));

		//PlannerPlaceDTO
		//List<PlannerPlace> list = dbplanner.getPlannerPlaceList();
		List<PlannerPlace> list = plannerPlaceRep.findAllByPlannerIdByPlannerPlaceDateAsc(dbplanner.getPlannerId());
		List<PlannerPlaceDTO> result = new ArrayList<PlannerPlaceDTO>();
		
		for(PlannerPlace x :list) {
			result.add(new PlannerPlaceDTO(x.getPlannerPlaceId(), x.getUser().getUserId(), x.getPlanner().getPlannerId(), x.getPlannerPlaceDate()
					, x.getPlace().getPlaceId(), x.getPlace().getPlaceCategory(), x.getPlace().getPlaceName(), x.getPlace().getPlaceLatitude(), x.getPlace().getPlaceLongitude()));
		}
		
		return result;
	}
	
	
	@Override
	public void insertPlan(Planner planner) {
		//planner insert
		plannerRep.save(planner);

		//diary insert
		/*if(planner.getPlannerName()==null) {
			diaryRep.save(new Diary(null, planner.getUser(), planner, null, DIARY_DEFAULT_NAME, planner.getPlannerType(), planner.getPlannerCount(),null));
		}else {
			diaryRep.save(new Diary(null, planner.getUser(), planner, null, planner.getPlannerName(), planner.getPlannerType(), planner.getPlannerCount(),null));
		}*/
		
		System.out.println("diary save완료");
	}
	
	@Override
	public void updatePlan(Planner planner) {
		Planner dbPlanner = plannerRep.findById(planner.getPlannerId())
				.orElseThrow( ()-> new RuntimeException("플래너를 찾을 수 없습니다."));

		dbPlanner.setPlannerName(planner.getPlannerName());
		dbPlanner.setPlannerType(planner.getPlannerType());
		dbPlanner.setPlannerCount(planner.getPlannerCount());
		dbPlanner.setPlannerStart(planner.getPlannerStart());
		dbPlanner.setPlannerEnd(planner.getPlannerEnd());
		dbPlanner.setPlannerState(planner.getPlannerState());
	}
	
	@Override
	public void deletePlan(Long plannerId) {
		
		plannerRep.deleteById(plannerId);
		

	}

	@Override
	public void insertPlanPlace(PlannerPlace plannerPlace) {
		//place 담은수 증가
		Place place = placeRep.findById(plannerPlace.getPlace().getPlaceId())
				.orElseThrow( ()-> new RuntimeException("존재하지 않는 장소 정보입니다."));
		place.setPlaceSave(place.getPlaceSave()+1);
		
		//plannerplace insert
		plannerPlaceRep.save(plannerPlace);
		
		//diary검색
		//Diary dbDiary =diaryRep.findByPlannerId(plannerPlace.getPlanner().getPlannerId());
		//diaryline insert
		//diaryLineRep.save(new DiaryLine(null, dbDiary, plannerPlace, null, null, 0, null));

	}

	

	@Override
	public void updatePlanPlace(PlannerPlace plannerPlace) {
		PlannerPlace dbplan = plannerPlaceRep.findById(plannerPlace.getPlannerPlaceId())
				.orElseThrow( ()-> new RuntimeException("플래너 일정을 찾을 수 없습니다."));
		dbplan.setPlannerPlaceDate(plannerPlace.getPlannerPlaceDate());
		
		//diary검색
		//Diary dbDiary =diaryRep.findByPlannerId(plannerPlace.getPlanner().getPlannerId());
		//diaryline update
		//DiaryLine dbDiaryLine = diaryLineRep.findById(diaryLine.getDiaryLineId())
				//.orElseThrow(() -> new RuntimeException("다이어리 상세 내용을 찾을 수 없습니다."));
	}
	
	@Override
	public void deletePlanPlace(Long plannerPlaceId) {
		PlannerPlace dbplan = plannerPlaceRep.findById(plannerPlaceId)
				.orElseThrow( ()-> new RuntimeException("플래너 일정을 찾을 수 없습니다."));
		
		//담은 수 감소
		Place place = placeRep.findById(dbplan.getPlace().getPlaceId())
				.orElseThrow( ()-> new RuntimeException("존재하지 않는 장소 정보입니다."));
		place.setPlaceSave(place.getPlaceSave()-1);
		
		plannerPlaceRep.deleteById(plannerPlaceId);
		
		//diaryline delete
	}

	

	@Override
	public void PlannerShareBoard(Long placeId) {
		
		
		
	}

	@Override
	public void plannerTypeUpdate(String type,Long plannerId) {
		Planner planner =plannerRep.findById(plannerId)
				.orElseThrow(()-> new RuntimeException("존재하지 않는 플래너입니다."));
		planner.setPlannerType(type);
		
		System.out.println("성공 "+planner.getPlannerType());
		
	}


}
