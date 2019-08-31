package miaosha.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import miaosha.result.Result;

/**
 * 异常拦截器
 * @author kaifeng1
 *
 */
@ControllerAdvice
@ResponseBody
public class AppliCationExceptionHandler {

	@ExceptionHandler(value=Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
		e.printStackTrace();
		if(e instanceof ApplicationException) {
			ApplicationException ex = (ApplicationException)e;
			return Result.error(ex.getErrorCode(),ex.getMsg());
		}else if(e instanceof BindException) {
			BindException ex = (BindException)e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError error = errors.get(0);
			String msg = error.getDefaultMessage();
			return Result.errorArgs(msg, errors);
		}
		return Result.error(e.getMessage()) ;
	}
}
