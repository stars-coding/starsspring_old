package com.stars.starsspring.framework.util;

import cn.hutool.core.lang.Assert;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 数字工具——类
 * 是一个实用工具类，提供了处理数字的方法，包括数字的转换和解析。
 * <p>
 * <p>
 * 属性字段：
 * STANDARD_NUMBER_TYPES
 * LONG_MIN
 * LONG_MAX
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * convertNumberToTargetClass
 * checkedLongValue
 * raiseOverflowException
 * parseNumber
 * parseNumber
 * trimAllWhitespace
 * hasLength
 * isHexNumber
 * decodeBigInteger
 *
 * @author stars
 */
public class NumberUtils {

    // 标准数字类型
    public static final Set<Class<?>> STANDARD_NUMBER_TYPES;
    // 最小长度
    private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);
    // 最大长度
    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

    /**
     * 静态代码块
     */
    static {
        Set<Class<?>> numberTypes = new HashSet<>(8);
        numberTypes.add(Byte.class);
        numberTypes.add(Short.class);
        numberTypes.add(Integer.class);
        numberTypes.add(Long.class);
        numberTypes.add(BigInteger.class);
        numberTypes.add(Float.class);
        numberTypes.add(Double.class);
        numberTypes.add(BigDecimal.class);
        STANDARD_NUMBER_TYPES = Collections.unmodifiableSet(numberTypes);
    }

    /**
     * 转换数字至给定类型（数字对象，指定的类型）
     * 将给定的数字转换为指定目标类的实例。
     *
     * @param number      要转换的数字对象
     * @param targetClass 要转换的目标类型
     * @param <T>         转换后的目标类的泛型类型
     * @return 转换后的数字，类型为目标类的实例
     * @throws IllegalArgumentException 如果目标类不受支持（即不是JDK中包含的标准数字子类）或发生溢出，则抛出IllegalArgumentException异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T convertNumberToTargetClass(Number number, Class<T> targetClass)
            throws IllegalArgumentException {
        Assert.notNull(number, "Number must not be null");
        Assert.notNull(targetClass, "Target class must not be null");
        if (targetClass.isInstance(number)) {
            return (T) number;
        } else if (Byte.class == targetClass) {
            long value = NumberUtils.checkedLongValue(number, targetClass);
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                NumberUtils.raiseOverflowException(number, targetClass);
            }
            return (T) Byte.valueOf(number.byteValue());
        } else if (Short.class == targetClass) {
            long value = NumberUtils.checkedLongValue(number, targetClass);
            if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                NumberUtils.raiseOverflowException(number, targetClass);
            }
            return (T) Short.valueOf(number.shortValue());
        } else if (Integer.class == targetClass) {
            long value = NumberUtils.checkedLongValue(number, targetClass);
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                NumberUtils.raiseOverflowException(number, targetClass);
            }
            return (T) Integer.valueOf(number.intValue());
        } else if (Long.class == targetClass) {
            long value = NumberUtils.checkedLongValue(number, targetClass);
            return (T) Long.valueOf(value);
        } else if (BigInteger.class == targetClass) {
            if (number instanceof BigDecimal) {
                return (T) ((BigDecimal) number).toBigInteger();
            } else {
                return (T) BigInteger.valueOf(number.longValue());
            }
        } else if (Float.class == targetClass) {
            return (T) Float.valueOf(number.floatValue());
        } else if (Double.class == targetClass) {
            return (T) Double.valueOf(number.doubleValue());
        } else if (BigDecimal.class == targetClass) {
            return (T) new BigDecimal(number.toString());
        } else {
            throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
                    number.getClass().getName() + "] to unsupported target class [" + targetClass.getName() + "]");
        }
    }

    /**
     * 检查Long值（数字对象，指定的类型）
     * 在将给定的数字对象转换为long值之前，检查溢出情况。
     *
     * @param number      要转换的数字对象
     * @param targetClass 目标类型
     * @return 如果可以转换而不发生溢出，则返回long值
     * @throws IllegalArgumentException 如果发生溢出，则抛出IllegalArgumentException异常
     */
    private static long checkedLongValue(Number number, Class<? extends Number> targetClass) {
        BigInteger bigInt = null;
        if (number instanceof BigInteger) {
            bigInt = (BigInteger) number;
        } else if (number instanceof BigDecimal) {
            bigInt = ((BigDecimal) number).toBigInteger();
        }
        if (bigInt != null && (bigInt.compareTo(NumberUtils.LONG_MIN) < 0 || bigInt.compareTo(NumberUtils.LONG_MAX) > 0)) {
            NumberUtils.raiseOverflowException(number, targetClass);
        }
        return number.longValue();
    }

    /**
     * 抛出溢出异常（数字对象，指定的类型）
     * 抛出一个溢出异常，指示无法将给定的数字对象转换为目标类型。
     *
     * @param number      转换的数字对象
     * @param targetClass 转换为的目标类型
     * @throws IllegalArgumentException 如果发生溢出，则抛出IllegalArgumentException异常
     */
    private static void raiseOverflowException(Number number, Class<?> targetClass) {
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
                number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
    }

    /**
     * 解析数字（文本，指定的类型）
     * 将给定的文本解析为目标类的数字对象。
     *
     * @param text        要转换的文本
     * @param targetClass 要解析成的目标类型
     * @return 解析后的数字
     * @throws IllegalArgumentException 如果目标类不受支持（即不是JDK中包含的标准Number子类），则抛出IllegalArgumentException异常
     * @see Byte#decode
     * @see Short#decode
     * @see Integer#decode
     * @see Long#decode
     * @see #decodeBigInteger(String)
     * @see Float#valueOf
     * @see Double#valueOf
     * @see BigDecimal#BigDecimal(String)
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
        Assert.notNull(text, "Text must not be null");
        Assert.notNull(targetClass, "Target class must not be null");
        String trimmed = NumberUtils.trimAllWhitespace(text);
        if (Byte.class == targetClass) {
            return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        } else if (Short.class == targetClass) {
            return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        } else if (Integer.class == targetClass) {
            return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        } else if (Long.class == targetClass) {
            return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
        } else if (BigInteger.class == targetClass) {
            return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
        } else if (Float.class == targetClass) {
            return (T) Float.valueOf(trimmed);
        } else if (Double.class == targetClass) {
            return (T) Double.valueOf(trimmed);
        } else if (BigDecimal.class == targetClass || Number.class == targetClass) {
            return (T) new BigDecimal(trimmed);
        } else {
            throw new IllegalArgumentException(
                    "Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
        }
    }

    /**
     * 解析数字（文本，指定的类型，数字格式对象）
     * 将给定的文本解析为目标类的数字对象。
     *
     * @param text         要转换的文本
     * @param targetClass  要解析成的目标类型
     * @param numberFormat 数字格式对象
     * @return 解析后的数字
     * @throws IllegalArgumentException 如果目标类不受支持（即不是JDK中包含的标准Number子类），则抛出IllegalArgumentException异常
     * @see NumberFormat#parse
     * @see #convertNumberToTargetClass
     * @see #parseNumber(String, Class)
     */
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass, @Nullable NumberFormat numberFormat) {
        if (numberFormat != null) {
            Assert.notNull(text, "Text must not be null");
            Assert.notNull(targetClass, "Target class must not be null");
            DecimalFormat decimalFormat = null;
            boolean resetBigDecimal = false;
            if (numberFormat instanceof DecimalFormat) {
                decimalFormat = (DecimalFormat) numberFormat;
                if (BigDecimal.class == targetClass && !decimalFormat.isParseBigDecimal()) {
                    decimalFormat.setParseBigDecimal(true);
                    resetBigDecimal = true;
                }
            }
            try {
                Number number = numberFormat.parse(NumberUtils.trimAllWhitespace(text));
                return NumberUtils.convertNumberToTargetClass(number, targetClass);
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse number: " + ex.getMessage());
            } finally {
                if (resetBigDecimal) {
                    decimalFormat.setParseBigDecimal(false);
                }
            }
        } else {
            return NumberUtils.parseNumber(text, targetClass);
        }
    }

    /**
     * 修剪全部空白（字符串）
     * 从给定的字符串中删除所有空白字符（包括空格、制表符和换行符），并返回结果。
     * 如果输入字符串为null或空字符串，将直接返回原始字符串。
     *
     * @param str 要处理的字符串
     * @return 删除空白字符后的字符串，或原始字符串（如果为null或空字符串）
     */
    public static String trimAllWhitespace(String str) {
        if (!NumberUtils.hasLength(str)) {
            return str;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 固有长度（字符串）
     * 检查给定的字符串是否既不为null 也不为空字符串。
     *
     * @param str 要检查的字符串
     * @return 如果字符串不为null且不为空字符串，则返回true；否则返回false
     */
    public static boolean hasLength(@Nullable String str) {
        return (str != null && !str.isEmpty());
    }

    /**
     * 是否为十六进制数字（字符串）
     * 检查给定的字符串是否表示十六进制数值。
     *
     * @param value 要检查的字符串
     * @return 如果字符串表示十六进制数值，则返回true；否则返回false
     */
    private static boolean isHexNumber(String value) {
        int index = (value.startsWith("-") ? 1 : 0);
        return (value.startsWith("0x", index)
                || value.startsWith("0X", index)
                || value.startsWith("#", index));
    }

    /**
     * 解析BigInteger类型（字符串）
     * 解析给定的字符串为BigInteger对象。
     *
     * @param value 要解析的字符串
     * @return 解析后的BigInteger对象
     * @throws NumberFormatException 如果字符串不符合有效的整数表示法，则抛出NumberFormatException异常
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;
        if (value.startsWith("-")) {
            negative = true;
            index++;
        }
        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (value.startsWith("#", index)) {
            index++;
            radix = 16;
        } else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;
        }
        BigInteger result = new BigInteger(value.substring(index), radix);
        return (negative ? result.negate() : result);
    }
}
