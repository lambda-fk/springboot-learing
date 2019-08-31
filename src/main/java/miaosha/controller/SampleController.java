package miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import miaosha.dao.domain.User;
import miaosha.result.Result;
import miaosha.service.UserService;

/**
 * srpingboot的使用，无需tomcat的单独配置
 * 我们运行这个main函数 然后 输入 http://www.localhost:8080/
 * 需要一个启动的类专门负责服务的启动
 * 然后就是其他的controller
 * @author kaifeng1
 *
 */
@Controller
@RequestMapping("/sample")
public class SampleController {
	
	@Autowired
	private UserService userService ;

	@RequestMapping("/hello")
	@ResponseBody
	Result<String> hello() {
		return Result.sucess("hello world !");
	}
	
	/**
	 * 读取数据库 并且展示到 thymeleaf模板上
	 * @param model
	 * @return
	 */
	@RequestMapping("/thymeleaf")
	public String toHelloHtml(Model model) {
		User user  = this.userService.findUserById(1L);
		model.addAttribute("name", user.getName());
		return "hello" ;
	}
	
	/**
	 * 插入数据库
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	Result<String> insert() {
		this.userService.insert(new User(3L,"新插入数据"));
		return Result.sucess("插入成功 !");
	}
	
}
