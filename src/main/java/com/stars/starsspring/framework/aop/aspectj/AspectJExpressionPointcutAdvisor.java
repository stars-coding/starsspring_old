package com.stars.starsspring.framework.aop.aspectj;

import com.stars.starsspring.framework.aop.Pointcut;
import com.stars.starsspring.framework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 切面表达式顾问——类
 * 用于基于AspectJ表达式的切面配置。它可以通过设置表达式和拦截方法来配置切面的行为。
 * <p>
 * <p>
 * 属性字段：
 * pointcut
 * advice
 * expression
 * <p>
 * 重写方法：
 * getPointcut
 * getAdvice
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * setAdvice
 * setExpression
 *
 * @author stars
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 切面表达式对象
    private AspectJExpressionPointcut pointcut;
    // 通知对象
    private Advice advice;
    // 表达式对象
    private String expression;

    /**
     * 获取切点
     * 获取与此切点通知器关联的切点对象。
     *
     * @return 与此切点通知器关联的切点对象
     */
    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    /**
     * 获取通知
     * 获取与此顾问关联的通知对象。
     *
     * @return 通知对象
     */
    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
