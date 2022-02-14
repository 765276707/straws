package com.gitee.pristine.domain.jsr303.annotation;

import com.gitee.pristine.domain.jsr303.validator.FieldValueEqualsTrueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 指定两个字段的值相等
 * @author Pristine Xu
 * @date 2022/2/4 4:41
 * @description:
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FieldValueEqualsTrueValidator.class})
public @interface FieldValueEqualsTrue {

    // 源字段
    String sourceField();

    // 目标字段
    String targetField();

    // 提示信息
    String message() default "两个字段的值不一致";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
