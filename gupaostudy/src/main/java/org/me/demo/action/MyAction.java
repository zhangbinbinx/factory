package org.me.demo.action;

import org.me.annotation.Autowired;
import org.me.annotation.Controller;
import org.me.annotation.RequestMapping;
import org.me.annotation.RequestParam;
import org.me.demo.service.IModifyService;
import org.me.demo.service.IQueryService;
import org.me.web.mvc.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 公布接口url
 * @author Tom
 *
 */
@Controller
@RequestMapping("/web")
public class MyAction {

	@Autowired
	IQueryService queryService;
	/*@Autowired
	IModifyService modifyService;*/

	@RequestMapping("/query.json")
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
							  @RequestParam("name") String name){
		String result = queryService.query(name);
		return out(response,result);
	}
	
	/*@RequestMapping("/add*.json")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,
			   @RequestParam("name") String name,@RequestParam("addr") String addr){
		String result = null;
		try {
			result = modifyService.add(name,addr);
			return out(response,result);
		} catch (Exception e) {
//			e.printStackTrace();
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("detail",e.getMessage());
//			System.out.println(Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
			model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
			return new ModelAndView("500",model);
		}

	}
	
	@RequestMapping("/remove.json")
	public ModelAndView remove(HttpServletRequest request,HttpServletResponse response,
		   @RequestParam("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}
	
	@RequestMapping("/edit.json")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id") Integer id,
			@RequestParam("name") String name){
		String result = modifyService.edit(id,name);
		return out(response,result);
	}*/
	
	
	
	private ModelAndView out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
