package miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import miaosha.annotation.Mobile;
import miaosha.util.ValidatorUtil;
import org.springframework.util.StringUtils;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
	private boolean required = false;
	/**
	 * 初始化
	 */
	public void initialize(Mobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	/**
	 * 校验
	 */
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (required) {
			return ValidatorUtil.isMobile(value);
		} else {
			if (StringUtils.isEmpty(value)) {
				return true;
			} else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
