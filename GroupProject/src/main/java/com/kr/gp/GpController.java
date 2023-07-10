package com.kr.gp;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dao.GpDAO;
import vo.GpVO;

@Controller
public class GpController {
	
	@Autowired//자동주입
	HttpServletRequest request;
	
	@Autowired
	ServletContext application;//애플리케이션의 정보를 담고있는 클래스
	
	public static final String PATH = "/WEB-INF/views/gp/";
	
	GpDAO gp_dao;
	public void setGp_dao(GpDAO gp_dao) {
		this.gp_dao = gp_dao;
	}
	
	//쇼핑메인 & 상품 전체목록 보기
	@RequestMapping(value={"/", "/list.do"})
	public String vsList( Model model ) {
		
		List<GpVO> list = gp_dao.selectList();
		model.addAttribute("list", list);
		return PATH + "gp_list.jsp";
		
	}
	//관리자모드 상품등록 하기
	@RequestMapping("/addproduct_form.do")
	public String addproduct_form() {
		return PATH + "gp_addproduct_form.jsp";
	}
	
	//관리자모드 상품 등록
	@RequestMapping("/addproduct.do")  
	public String addproduct(GpVO vo) {
		/*
		 * String ip = request.getRemoteAddr(); vo.setIp(ip);
		 */
		//절대경로를 찾기위한 준비
		String webPath = "/resources/upload/";
		String savePath = application.getRealPath(webPath);
		System.out.println("절대경로 : " + savePath);
		
		//업로드를 위해 보내진 photo라는 이름의 파일정보
		MultipartFile photo = vo.getPhoto();
		
		String filename = "no_file";
		
		//업로드 된 파일이 존재한다면
		if(!photo.isEmpty()) {
			//업로드된 실제 파일의 이름
			filename = photo.getOriginalFilename();
			
			//저장할 파일의 경로
			File saveFile = new File(savePath, filename);
			
			if(!saveFile.exists() ) {
				saveFile.mkdirs();
			}else {
				//동일파일명에 대한 이름 수정
				long time = System.currentTimeMillis();
				filename = String.format("%d_%s", time, filename);
				saveFile = new File(savePath, filename);
			}
			
			try {
				photo.transferTo(saveFile);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//vo에 파일명 세팅 후 DB에 add
		vo.setFilename(filename);
		System.out.print("fn" + vo.getFilename());
		gp_dao.addProduct(vo);
		
		
		//Controller에서 url매핑을 호출해야 하는 경우
		//sendRedirect("list.do");
		return "redirect:list.do";
		
	}
	
	//관리자모드 상품삭제 페이지로 전환 
		@RequestMapping("/delete_form.do")
		public String delete_form() {
			return PATH + "gp_delete_form.jsp";
		}
	
	//상품 삭제
	@RequestMapping(value="/delete.do", produces="application/json;charset=UTF-8")
	/*
	 * @ResponseBody 
	 */	
	public String delproduct(int pid ) {
		
		GpVO vo = gp_dao.selectOne(pid);
		
		int res = gp_dao.deleteProduct(pid);
		/*String result = "삭제 실패";
		
		if(res != 0 ) {
			result = "삭제 성공";*/
			//이미지 업로드된 게시글의 이미지를 삭제하기
			String webPath = "/resources/upload/";
			String savePath = application.getRealPath(webPath);
			
			String photo = vo.getFilename();
			
			File file= new File(savePath, photo);
			/*if(file.exists()) {*/
				file.delete();
				/* } */
			
		
		
		/* String str = String.format("[{'result':'%s'}]", result); */
		
		
		
		
		return "redirect:list.do"; 
	}
	
	//상품 수정을 위한 페이지로 전환
	@RequestMapping("/delete&modify_form.do")
	public String modify_form(Model model, GpVO vo) {
		
		model.addAttribute("vo", vo);
		return PATH + "gp_delete&modify_form.jsp";
		
	}
	
	// 상품 수정
	@RequestMapping("/modify.do")
	public String modify(GpVO vo) {
		
		System.out.println("filename : " + vo.getFilename());
		String webPath = "/resources/upload/";
		String savePath = application.getRealPath(webPath);
		System.out.println("절대경로 : " + savePath);
		
		//업로드를 위해 보내진 photo라는 이름의 파일정보
		MultipartFile photo = vo.getPhoto();
		
		String filename = "no_file";
		
		//업로드 된 파일이 존재한다면
		if(!photo.isEmpty()) {
			//업로드된 실제 파일의 이름
			filename = photo.getOriginalFilename();
			
			//저장할 파일의 경로
			File saveFile = new File(savePath, filename);
			
			if(!saveFile.exists() ) {
				saveFile.mkdirs();
			}else {
				//동일파일명에 대한 이름 수정
				long time = System.currentTimeMillis();
				filename = String.format("%d_%s", time, filename);
				saveFile = new File(savePath, filename);
			}
			
			try {
				photo.transferTo(saveFile);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		vo.setFilename(filename);
		System.out.print("fn" + vo.getFilename());
		gp_dao.update_modify(vo);
		
		
		int res = gp_dao.update_modify(vo);
		return "redirect:list.do"; 
	}
	//쇼핑메인 & 사료 상품 목록보기
		@RequestMapping(value={"/feedlist.do"})
		public String feedList( Model model ) {
			
			List<GpVO> feedlist = gp_dao.selectfeedList(); /* dao에 클래스명을 넣어야함! sqlsession.sdfsdf 아님! */
			model.addAttribute("feedlist", feedlist);
			
			System.out.println("aa:"+feedlist.size());
			return PATH + "gp_feedlist.jsp";
			
		}
		//쇼핑메인 & 목줄 상품 목록보기
				@RequestMapping(value={"/leashlist.do"})
				public String leashList( Model model ) {
					
					List<GpVO> leashlist = gp_dao.selectleashList(); /* dao에 클래스명을 넣어야함! sqlsession.sdfsdf 아님! */
					model.addAttribute("leashlist", leashlist);
					
					System.out.println("aa:"+leashlist.size());
					return PATH + "gp_leashlist.jsp";
					
				}
		//쇼핑메인 & 패드 상품 목록보기
				@RequestMapping(value={"/padlist.do"})
				public String padList( Model model ) {
					
					List<GpVO> padlist = gp_dao.selectpadList(); /* dao에 클래스명을 넣어야함! sqlsession.sdfsdf 아님! */
					model.addAttribute("padlist", padlist);
					
					System.out.println("aa:"+padlist.size());
					return PATH + "gp_padlist.jsp";
					
				}
		//쇼핑메인 & 옷 상품 목록보기
				@RequestMapping(value={"/clothlist.do"})
				public String clothList( Model model ) {
					
					List<GpVO> clothlist = gp_dao.selectclothList(); /* dao에 클래스명을 넣어야함! sqlsession.sdfsdf 아님! */
					model.addAttribute("clothlist", clothlist);
					
					System.out.println("aa:"+clothlist.size());
					return PATH + "gp_clothlist.jsp";
					
				}
	
	
}

	// 관리자모드 상품등록 폼으로 전환
	
	// 관리자모드 상품등록 하기
	
	// 관리자모드 상품 삭제
	
	// 관리자모드 상품 수정을 위한 페이지로 전환
	
	// 관리자모드 상품 수정