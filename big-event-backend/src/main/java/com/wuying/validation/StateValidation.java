package com.wuying.validation;

import com.wuying.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/**
 * 实现状态验证的类，用于检查给定的状态值是否符合规范
 * 该类实现了ConstraintValidator接口，用于Hibernate Validator框架进行自定义校验
 */
public class StateValidation implements ConstraintValidator<State,String> {

    /**
     * 校验状态值是否有效的核心方法
     *
     * @param value 待校验的状态值
     * @param context 校验器上下文，用于在验证过程中添加错误信息或者调整验证行为
     * @return 如果状态值有效返回true，否则返回false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //提供校验规则
        if (value == null){
            // 如果值为null，则认为校验不通过
            return false;
        }
        // 如果值等于"已发布"或"草稿"，则认为校验通过
        if (value.equals("已发布") || value.equals("草稿")){
            return true;
        }
        // 其他情况下，校验不通过
        return false;
    }
}


