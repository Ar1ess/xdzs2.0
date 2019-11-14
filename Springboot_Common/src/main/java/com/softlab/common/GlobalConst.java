package com.softlab.common;

/**
 * @author LiXiwen
 * @date 2019/11/8 11:28
 */
public class GlobalConst {

    public static final String appid = "wxde7de0ed99d29c9e";

    public static final String secret = "e44118aef2f5d33497615483d7af3b15";

    public static final String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=";

    public static final String url0 = "&grant_type=authorization_code";


    /**
     * 文章标题等简介
     */
    public static final String ARTICLE_INTR = "redis:article:intr";

    /**
     * 文章总数
     */
    public static final String ARTICLE_COUNT = "redis:article:count";


    /**
     * 步数排行榜
     */
    public static final String PACE_RANK = "pace_rank";


    public static final String PACE_LIST = "paceList";

    public static final String USER_SIZE = "userSize";

    public static final String PACE_PREFIX = "bean:";

    public static final String PACE_SORT = "pace_sort";




}
