package com.stars.starsspring.framework.aop.aspectj;

import com.stars.starsspring.framework.aop.ClassFilter;
import com.stars.starsspring.framework.aop.MethodMatcher;
import com.stars.starsspring.framework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 切面表达式——类
 * 基于AspectJ表达式的切点，用于匹配目标类和方法是否符合特定的表达式。
 * <p>
 * <p>
 * 属性字段：
 * SUPPORTED_PRIMITIVES
 * pointcutExpression
 * <p>
 * 重写方法：
 * matches
 * matches
 * getClassFilter
 * getMethodMatcher
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * AspectJExpressionPointcut
 *
 * @author stars
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    // 支持原始Map对象，支持的PointcutPrimitive类型集合
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    // 切点表达式对象，AspectJ表达式解析后的对象
    private final PointcutExpression pointcutExpression;

    // 静态块，在类加载时初始化支持的PointcutPrimitive类型集合
    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    /**
     * 有参构造函数（表达式）
     *
     * @param expression AspectJ表达式
     */
    public AspectJExpressionPointcut(String expression) {
        // 创建AspectJ表达式解析器
        PointcutParser pointcutParser =
                PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        AspectJExpressionPointcut.SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        // 解析表达式
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 匹配（类对象）
     * 测试给定的类对象是否匹配条件。
     *
     * @param clazz 要测试的类对象
     * @return 如果类对象匹配条件，返回true；否则返回false
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return this.pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * 匹配（方法对象，目标类对象）
     * 检查给定的目标方法是否匹配特定的规则。
     *
     * @param method      要匹配的目标方法对象
     * @param targetClass 目标类对象
     * @return 如果方法匹配规则，则返回true；否则返回false
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return this.pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    /**
     * 获取类过滤器
     * 获取与切点关联的类过滤器对象。
     *
     * @return 与切点关联的类过滤器对象
     */
    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    /**
     * 获取方法匹配器
     * 获取与切点关联的方法匹配器对象。
     *
     * @return 与切点关联的方法匹配器对象
     */
    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
