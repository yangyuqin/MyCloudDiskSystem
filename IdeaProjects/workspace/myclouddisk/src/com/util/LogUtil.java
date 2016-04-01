package com.util;

/**
 * Created by yyq on 11/25/15.
 */
public class LogUtil {
    public static final String LOG_SPLIT = " ";
    public static final String LOG_MARKS_QUOTATION = "\"";

    public static String getLogStr(String functionName, String status,
                                   Object inputParams, Object outputParams, String exceptionMsg) {
        StringBuffer sb = new StringBuffer();
        sb.append(functionName).append(LOG_SPLIT);

        sb.append(status).append(LOG_SPLIT);

        sb.append(LOG_MARKS_QUOTATION);
        sb.append(inputParams != null ? inputParams.toString() : "");
        sb.append(LOG_MARKS_QUOTATION);
        sb.append(LOG_SPLIT);

        sb.append(LOG_MARKS_QUOTATION);
        sb.append(outputParams != null ? outputParams.toString() : "");
        sb.append(LOG_MARKS_QUOTATION);
        sb.append(LOG_SPLIT);

        sb.append(LOG_MARKS_QUOTATION);
        sb.append(exceptionMsg != null ? exceptionMsg : "");
        sb.append(LOG_MARKS_QUOTATION);
        return sb.toString();
    }
}
