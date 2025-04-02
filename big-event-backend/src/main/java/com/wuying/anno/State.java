package com.wuying.anno;

import com.wuying.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@code State} 注解用于验证字段的值是否为 '已发布' 或 '草稿'。
 * 该注解主要应用于需要对状态字段进行约束的场景，以确保状态值的正确性和一致性。
 *
 * @author [Your Name]
 * @see Constraint
 * @see Documented
 * @see Retention
 * @see Target
 */
@Documented// 元注解，表示该注解会被javadoc工具文档化
@Constraint(validatedBy = {StateValidation.class }) // 元注解，用于定义约束，此处未指定验证器
@Target({ FIELD })// 元注解，表示该注解只能用于字段
@Retention(RUNTIME)// 元注解，表示该注解在运行时可用
public @interface State {
    /**
     * 获取验证失败时的消息。
     *
     * @return 验证失败时的默认消息 "state参数值只能是'已发布'或'草稿'"。
     */
    String message() default "state参数值只能是'已发布'或'草稿'";

    /**
     * 获取应用该注解的元素所属于的组。
     *
     * @return 默认为空数组，表示属于所有组。
     */
    //指定分组
    Class<?>[] groups() default { };

    /**
     * 获取负载，即附加的信息或数据。
     *
     * @return 默认为空数组，表示没有负载。
     */
    //负载
    Class<? extends Payload>[] payload() default { };
}






