package miaosha.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import miaosha.result.Result;
import miaosha.service.UserService;
import miaosha.vo.login.LoginVo;

/**
 * 
 * @author kaifeng1
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService userService ;
	
	/**
	 * 跳转到登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toLogin")
	String toLogin(Model model) {
		return "login";
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo vo) throws Exception {
		log.info(vo.toString());
		this.userService.login(response, vo);
		return Result.sucess(Boolean.TRUE);
	}
}
