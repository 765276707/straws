package com.gitee.pristine.domain.jsr303.validator;

import cn.hutool.core.util.ReflectUtil;
import com.gitee.pristine.domain.jsr303.annotation.FieldValueEqualsTrue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Pristine Xu
 * @date 2022/2/4 4:43
 * @description:
 */
public class FieldValueEqualsTrueValidator implements ConstraintValidator<FieldValueEqualsTrue, Object> {

    private String sourceField;
    private String targetField;

    @Override
    public void initialize(FieldValueEqualsTrue constraintAnnotation) {
        this.sourceField = constraintAnnotation.sourceField();
        this.targetField = constraintAnnotation.targetField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object targetValue = ReflectUtil.getFieldValue(o, targetField);
            Object sourceValue = ReflectUtil.getFieldValue(o, sourceField);
            // 都为null
            if (targetValue==null && sourceValue==null) {
                return true;
            }
            // 判断是否一致
            return targetValue!=null && targetValue.equals(sourceValue);
        } catch (Exception e) {
            return false;
        }
    }

}
