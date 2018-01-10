package com.phemie.scnu.laolekang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class Regular {
    // 检验输入是否合法
    public static boolean regular(String s1, String s2){
        try {
            Pattern p = Pattern.compile(s1);
            Matcher m = p.matcher(s2);

            return m.matches();
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}
