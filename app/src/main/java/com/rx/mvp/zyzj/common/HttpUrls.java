package com.rx.mvp.zyzj.common;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class HttpUrls {

    public static final boolean isDebug = true;
    public static String BASE_API;

    static {
        if(isDebug){
            BASE_API = "http://39.108.7.224:8080/";
        }else{
            BASE_API = "http://39.108.7.224:3000/mock/15/";
        }
    }


//    public static final String BASE_API = "http://39.108.7.224:3000/mock/15/";

    public static final String ABOUT_URL = BASE_API + "auth-web/static/android/about.html";

    //注册
    public static final String API_Register = "auth-web/user/register";
    //退出登录
    public static final String API_Logout = "auth-web/user/logout";
    //登录
    public static final String API_Login = "auth-web/user/login";
    //修改密码
    public static final String API_Update_PSW= "auth-web/user/updatePassword";
    //获取用户信息
    public static final String API_GetUserInfo = "auth-web/user/getUserInfo";
    //版本更新
    public static final String API_GetVersion = "auth-web/user/getVersion";
    //意见反馈列表
    public static final String API_SuggestionList = "auth-web/suggestion/suggestionList";
    //提交意见反馈
    public static final String API_SubmitSuggestion = "auth-web/suggestion/submitSuggestion";
    //意见反馈详情
    public static final String API_SuggestionDetail = "auth-web/suggestion/suggestionDetail";
    //获取当前授权信息
    public static final String API_AUTHINFO= "pay-web/pay/authInfo";
    //获取购买授权信息
    public static final String API_GetBuyInfo= "pay-web/pay/getBuyInfo";
    //生成订单
    public static final String API_CreateOrder= "/pay-web/pay/getOrder";
    //查询订单
    public static final String API_FindOrder= "/pay-web/pay/findOrder";
    //获取彩种
    public static final String API_Lottery= "lottery-web/lottery/lottery";
    //获取首页头部开奖信息
    public static final String API_Lottery_Current_Info= "lottery-web/lottery/getLotteryCurrentInfo";
    //获取开奖历史数据
    public static final String API_award_Result= "lottery-web/lottery/awardResult";
    //用户计划
    public static final String API_UserPlan= "lottery-web/lottery/userPlan";
    //所有计划
    public static final String API_AllPlan= "lottery-web/lottery/allPlan";
    //修改用户计划
    public static final String API_UpdatePlan = "lottery-web/lottery/updateUserPlan";
    //获取首页列表信息
    public static final String API_HOME_PLAN = "lottery-web/lottery/guessPlanList";
    //切换公式
    public static final String API_Refresh_Plan = "lottery-web/lottery/refreshPlanList";
    //获取单个计划详情的预测列
    public static final String API_Plan_Details = "lottery-web/lottery/guessPlanSublist";
    //提交公式
    public static final String API_Submit_Formula = "lottery-web/lottery/submitLotteryFormula";


    //遗漏分析
    public static final String API_Analyze_Miss = "lottery-web/analyze/miss";
    //冷热分析
    public static final String API_Analyze_Hot = "lottery-web/analyze/hot";
    //指标遗漏分析
    public static final String API_Analyze_Target_Miss = "lottery-web/analyze/quotaMiss";
    //指标冷热分析
    public static final String API_Analyze_Target_Hot = "lottery-web/analyze/quotaHot";
    //遗漏分析筛选条件
    public static final String API_Miss_Config = "lottery-web/analyze/missConfig";
    //冷热分析筛选条件
    public static final String API_Hot_Config = "lottery-web/analyze/hotConfig";
    //指标遗漏分析筛选条件
    public static final String API_Target_Miss_Config = "lottery-web/analyze/quotaMissConfig";
    //指标冷热分析筛选条件
    public static final String API_Target_Hot_Config = "lottery-web/analyze/quotaHotConfig";



    // http://39.108.7.224:8080/lottery-web/lottery/userPlan

    //  http://39.108.7.224:8080/lottery-web/lottery/allPlan


}
