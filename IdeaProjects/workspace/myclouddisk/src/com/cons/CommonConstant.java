package com.cons;

/**
 * Created by yyq on 11/20/15.
 */

/**
 *整个应用通用的常量
 *<br><b>类描述:</b>
 *<pre>|</pre>
 *@see
 *@since
 */
public class CommonConstant {
    /**
     * 用户对象放到Session中的键名称
     */
    public static final String USER_CONTEXT = "USER_CONTEXT";

    /**
     * 将登录前的URL放到Session中的键名称
     */
    public static final String LOGIN_TO_URL = "toUrl";

    /**
     * 每页的记录数
     */
    public static final int PAGE_SIZE = 3;

    /**
     * HDFS访问地址
     */
    public static final String HDFS = "hdfs://localhost:9000/user/yyq/filesSpace";

    /**
     * 文件下载默认路径
     */
    public static final String LOCALPATH = "/home/yyq/Downloads/";

    /**
     * 文件上传中转站默认路径
     */
    public static String tempPath = "/home/yyq/Downloads/";

    /**
     * 文件上传中转站默认路径
     */
    public static String userNameRegrex = "^[a-zA-Z0-9]{1,14}$"; //用户名正则表达式：长度2-14，包括数字、大小写字母
    public static String passwordRegrex = "^[a-zA-Z0-9]+\\w{5,14}$"; //密码正则表达式：长度6-14，以字母数字开头，包括数字、字母和下划线
    public static String emailRegrex = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";


}
