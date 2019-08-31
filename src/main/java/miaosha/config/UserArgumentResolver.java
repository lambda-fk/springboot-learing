package miaosha.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import miaosha.dao.domain.User;
import miaosha.service.UserService;
import miaosha.util.ConstValue;


/**
 * user作为方法参数传入的处理对象
 * @author kaifeng1
 *
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	@Autowired
	private UserService userService ;
	
	/**
	 * 识别出需要传入的对象
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> claz = parameter.getParameterType();
		//将user这个对象加入到controller的方法参数中
		if(claz == User.class) {
			return true ;
		}
		return false;
	}

	/**
	 * 将制定的对象传入到controller方法中
	 */
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		Cookie cook = this.getCookie(request, ConstValue.COOKI_NAME_TOKEN);
		if(cook == null) {
			return null ;
		}
		String paramToken = request.getParameter(ConstValue.COOKI_NAME_TOKEN);
		if(StringUtils.isEmpty(cook.getValue()) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cook.getValue():paramToken;
		
		return userService.getByToken(response, token);
	}
	
	public Cookie getCookie(HttpServletRequest request , String name) {
		Cookie[] cooks = request.getCookies() ;
		if(cooks == null || cooks.length == 0) {
			return null ;
		}
		Cookie cook = null ;
		for(Cookie c : cooks) {
			if(name.equals(c.getName())) {
				cook = c ;
				break ;
			}
		}
		return cook ;
	}

}
