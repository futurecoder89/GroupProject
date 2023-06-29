package com.kr.gp;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.VisitDAO;
import vo.VisitVO;

@Controller
public class GpController {
	
	@Autowired//자동주입
	HttpServletRequest request;
	
	@Autowired
	ServletContext application;//애플리케이션의 정보를 담고있는 클래스
	
	public static final String PATH = "/WEB-INF/views/visit/";
	
	VisitDAO visit_dao;
	public void setVisit_dao(VisitDAO visit_dao) {
		this.visit_dao = visit_dao;
	}
	
	//쇼핑메인 & 상품 전체목록 보기
	@RequestMapping(value={"/", "/list.do"})
	public String vsList( Model model ) {
		
		List<VisitVO> list = visit_dao.selectList();
		model.addAttribute("list", list);
		return PATH + "visit_list.jsp";
		
	}

	// 관리자모드 상품등록 폼으로 전환
	
	// 관리자모드 상품등록 하기
	
	// 관리자모드 상품 삭제
	
	// 관리자모드 상품 수정을 위한 페이지로 전환
	
	// 관리자모드 상품 수정