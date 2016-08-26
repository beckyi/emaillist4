package kr.ac.sungkyul.emaillist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.sungkyul.emaillist.dao.EmailListDao;
import kr.ac.sungkyul.emaillist.vo.EmailListVo;

@Controller
public class EmailListController {
	
	@Autowired	//의존하고있음
	private EmailListDao dao;
	
//	@ResponseBody
//	@RequestMapping("/list")
//	public ModelAndView list(HttpServletRequest request){
//		List<EmailListVo> list = dao.getList();
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("list", list);
//		mav.setViewName("/WEB-INF/views/list.jsp");
//		System.out.println(list);
//		return mav;	//forwarding
//	}
	
	@RequestMapping("/list")
	public String list(Model model){
		List<EmailListVo> list = dao.getList();
		
		model.addAttribute("list",list);
		return "/WEB-INF/views/list.jsp";
	}
	
	@RequestMapping("/form")
	public String form(Model model){
		List<EmailListVo> list = dao.getList();
		
		model.addAttribute("list",list);
		return "/WEB-INF/views/form.jsp";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
//	@ResponseBody
	public String insert(@ModelAttribute EmailListVo vo){
		dao.insert(vo);
		return "redirect:/list";
	}
}
