package kosta.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kosta.mvc.domain.AskBoard;
import kosta.mvc.domain.AskReply;
import kosta.mvc.domain.Users;
import kosta.mvc.service.AskBoardService;
import kosta.mvc.service.UserService;
import lombok.RequiredArgsConstructor;

//@Slf4j
@Controller
@RequiredArgsConstructor
public class AskController {

	private final AskBoardService askBoardService;
	private final UserService userService;
	
	private final static int PAGE_COUNT=5;
	private final static int BLOCK_COUNT=3;
	
	
	/**전체검색 - board에서*/
	@RequestMapping("/board/AskList") 
	public void askList(Model model , @RequestParam(defaultValue="1") int nowPage) {
	
		Pageable pageable = PageRequest.of( (nowPage-1), PAGE_COUNT, Direction.DESC, "AskId");
		Page<AskBoard> pageList = askBoardService.getAllAskBoards(pageable); 
		
		int temp = (nowPage-1)%BLOCK_COUNT;
		int startPage = nowPage - temp;
		
		model.addAttribute("pageList",pageList);
        model.addAttribute("blockCount", BLOCK_COUNT);
        model.addAttribute("startPage", startPage);
        model.addAttribute("nowPage", nowPage);
		
	}
	
	/**전체검색 - admin에서*/
	@RequestMapping("/admin/AskList_Admin")
	public void askListAdmin(Model model , @RequestParam(defaultValue= "1") int nowPage) {
		
		Pageable pageable = PageRequest.of( (nowPage-1), PAGE_COUNT , Direction.DESC , "AskId");
		Page<AskBoard> pageList = askBoardService.getAllAskBoards(pageable);
		
		int temp = (nowPage-1)%BLOCK_COUNT;
		int startPage = nowPage - temp;
		
		System.out.println("adminList test");
					
		model.addAttribute("pageList",pageList);
        model.addAttribute("blockCount", BLOCK_COUNT);
        model.addAttribute("startPage", startPage);
        model.addAttribute("nowPage", nowPage);
	}
	

	/**글 등록폼*/
	@RequestMapping("/board/AskBoard")
	public void write() {
        System.out.println("askboard test");
	}

	
	/**글 등록*/
	@RequestMapping(value= "/board/Askinsert", method = RequestMethod.POST)
	public String insert(AskBoard askBoard , HttpSession session) {
		System.out.println("insert test");

		String uploadpath = session.getServletContext().getRealPath("/WEB-INF/") + "upload/ask/";
		askBoardService.addAskBoard(askBoard, uploadpath);

		return "redirect:/board/AskList";

	}

	
	/**상세보기 - board*/
	@RequestMapping("/board/Ask_Detail/{askId}")
	public String read(@PathVariable Long askId , Model model) {
		System.out.println("askboardId test");
		
		AskBoard askBoard = askBoardService.getAskBoard(askId);
        List<AskReply> replylist = askBoard.getAskReplyList();
		
        System.out.println("rrrr");
        System.out.println(replylist.size());
        
		model.addAttribute("askboard",askBoard);
		model.addAttribute("replylist",replylist);

		return "/board/Ask_Detail";
				
	}
	
	/**상세보기 답변달기 - admin*/
	@RequestMapping("/reply/AskReply_Write/{askId}")
	public ModelAndView read2(@PathVariable Long askId) {
		System.out.println("askboardId test");
		
		AskBoard askBoard = askBoardService.getAskBoard(askId);

		return new ModelAndView("reply/AskReply_Write", "askboard", askBoard);
		
	}
	
	/**관리자 상세보기*/
	@RequestMapping("/admin/AskDetail_Admin/{askId}")
	public String read2(@PathVariable Long askId , Model model) {
		System.out.println("askboardId test");
		
		AskBoard askBoard = askBoardService.getAskBoard(askId);
        List<AskReply> replylist = askBoard.getAskReplyList();
		
        System.out.println("rrrr");
        System.out.println(replylist.size());
        
		model.addAttribute("askboard",askBoard);
		model.addAttribute("replylist",replylist);

		return "/admin/AskDetail_Admin";
				
	}
	
	
		
		
	/**삭제*/
	@RequestMapping("/Askdelete")
	public String delete(Long askId) {
		
		System.out.println("delete test1");
		
		askBoardService.deleteAskBoard(askId);

		System.out.println("delete test2");
		
		return "redirect:/board/AskList";
	}
	
	/**관리자 삭제하기*/
	@RequestMapping("/admin/adelete")
	public String delete2(Long askId) {
		
		System.out.println("delete test11");
		
		askBoardService.deleteAskBoard(askId);

		System.out.println("delete test22");
		
		return "redirect:/admin/AskList_Admin";
	}
	
	
	/**답변 Y or N*/
	@RequestMapping("/admin/detail/complete") 
	public ModelAndView complete(Long askId, String btnradio) {
	
		 askBoardService.complete(askId , btnradio);
		 return new ModelAndView("redirect:/board/AskList");
	  
	}
	
   
	/**마이페이지에서 내가 쓴 글 목록 조회*/
	@RequestMapping("/mypage/myask")
	public String mylist(Model model) {
		
		Users users = (Users)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					
		List<AskBoard> myList = askBoardService.selectByUserId(users.getUserId());
		
		model.addAttribute("myList" , myList);
		model.addAttribute("users" , users);
		
		return "mypage/myask";
	}
	
	
}
