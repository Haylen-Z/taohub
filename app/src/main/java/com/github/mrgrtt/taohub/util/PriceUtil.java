package com.github.mrgrtt.taohub.util;

import java.math.BigDecimal;

public class PriceUtil {
    /**
     * 人民币转换（分转换为元）
     * @param m 分
     * @return 元（字符串）
     */
    static public String convert(long m) {
        return
        BigDecimal.valueOf(m)
                .divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
}
