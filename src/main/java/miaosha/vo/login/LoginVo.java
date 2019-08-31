package miaosha.vo.login;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import miaosha.annotation.Mobile;

/**
 * 登录的vo
 * 主要学习这个校验的用法
 * @author kaifeng1
 *
 */
public class LoginVo {

	@NotNull
	@Mobile
	private String mobile;

	@NotNull
	@Length(min = 32)
	private String password;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginVo [mobile=" + mobile + ", password=" + password + "]";
	}
}
