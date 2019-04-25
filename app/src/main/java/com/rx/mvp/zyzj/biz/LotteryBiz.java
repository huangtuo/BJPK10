package com.rx.mvp.zyzj.biz;

import com.rx.mvp.zyzj.base.BaseBiz;
import com.rx.mvp.zyzj.common.HttpUrls;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.rx.mvp.zyzj.net.retrofit.HttpRequest;
import com.rx.mvp.zyzj.utils.EncryptUtil;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class LotteryBiz extends BaseBiz {
    private static LotteryBiz lotteryBiz;

    private LotteryBiz() {

    }

    public static LotteryBiz getInstance() {
        if (lotteryBiz == null) {
            synchronized (LotteryBiz.class) {
                if (lotteryBiz == null)
                    return new LotteryBiz();
            }
        }
        return lotteryBiz;
    }

    /**
     * 获取授权信息列表
     *
     * @param lifecycle
     * @param callback
     */
    public void getAuthInfo(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_AUTHINFO, HttpRequest.Method.POST, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 获取彩种
     *
     * @param lifecycle
     * @param callback
     */
    public void getLottery(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_Lottery, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 获取首页头部开奖信息
     *
     * @param lifecycle
     * @param callback
     */
    public void getCurrentLotteryInfo(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_Lottery_Current_Info, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 创建订单
     * @param orderType 订单类型：1直接购买 2续费升级
     * @param goodsId 当前授权信息
     * @param lotteryType 彩种
     * @param payType  支付渠道：1其他 2支付宝 3微信
     * @param lifecycle
     * @param callback
     */
    public void createOrder(String orderType, String goodsId, String lotteryType, String payType, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("orderType", orderType);
        request.put("goodsId", goodsId);
        request.put("lotteryType", lotteryType);
        request.put("payType", payType);
        /**
         * 发送请求
         */
        getRequest().request(HttpUrls.API_CreateOrder, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    public void toFindOrder(String orderNo, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("orderNo", orderNo);
        /**
         * 发送请求
         */
        getRequest().request(HttpUrls.API_FindOrder, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    /**
     * 获取历史记录
     * @param lotteryId
     * @param page
     * @param size
     * @param lifecycle
     * @param callback
     */
    public void getHistoryDatas(String lotteryId, String page, String size, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("lotteryId", lotteryId);
        request.put("page", page);
        request.put("size", size);
        /**
         * 发送请求
         */
        getRequest().request(HttpUrls.API_award_Result, HttpRequest.Method.GET, request, lifecycle, callback);
    }

    /**
     * 获取用户计划
     * @param lifecycle
     * @param callback
     */
    public void getUserPlan(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_UserPlan, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 获取所有计划
     * @param lifecycle
     * @param callback
     */
    public void getAllPlan(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_AllPlan, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 修改用户计划
     * @param ids
     * @param lifecycle
     * @param callback
     */
    public void updatePlan(String ids,LifecycleProvider lifecycle, HttpRxCallback callback) {
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("ids", ids);
        getRequest().request(HttpUrls.API_UpdatePlan, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    /**
     * 获取首页计划列表
     * @param lifecycle
     * @param callback
     */
    public void getHomePlans(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_HOME_PLAN, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 切换公式
     * @param lifecycle
     * @param callback
     */
    public void refreshPlan(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_Refresh_Plan, HttpRequest.Method.POST, new TreeMap<>(), lifecycle, callback);
    }
    /**
     * 获取单个计划详情的预测列表
     * @param lifecycle
     * @param callback
     */
    public void getPlanDetails(String id, LifecycleProvider lifecycle, HttpRxCallback callback) {
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("lotteryPlanId", id);
        getRequest().request(HttpUrls.API_Plan_Details, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    /**
     *
     * @param id
     * @param resultNum 返回几个数//定码个数
     * @param chaseNum //连续追几个大周期(list的size)
     * @param giveUpNum  // index的最大值 //计划周期
     * @param scoreDown ,//正确率下限
     * @param scoreUp //正确率上限
     * @param continuousCorrectNumDown //连对下限
     * @param continuousCorrectNumUp //连对上限
     * @param continuousWrongNumDown /连错下限
     * @param continuousWrongNumUp //连错上限
     * @param currentContinuousNumDown //当前连对错上限
     * @param currentContinuousNumUp//当前连对错下限
     * @param lifecycle
     * @param callback
     */
    public void submitFormula(String id, String resultNum, String chaseNum, String giveUpNum, String scoreDown,
                              String scoreUp, String continuousCorrectNumDown, String continuousCorrectNumUp, String continuousWrongNumDown,
                              String continuousWrongNumUp, String currentContinuousNumDown, String currentContinuousNumUp,
                              LifecycleProvider lifecycle, HttpRxCallback callback) {
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("lotteryPlanId", id);
        request.put("resultNum", resultNum);
        request.put("chaseNum", chaseNum);
        request.put("giveUpNum", giveUpNum);
        request.put("scoreDown", scoreDown);
        request.put("scoreUp", scoreUp);
        request.put("continuousCorrectNumDown", continuousCorrectNumDown);
        request.put("continuousCorrectNumUp", continuousCorrectNumUp);
        request.put("continuousWrongNumDown", continuousWrongNumDown);
        request.put("continuousWrongNumUp", continuousWrongNumUp);
        request.put("currentContinuousNumDown", currentContinuousNumDown);
        request.put("currentContinuousNumUp", currentContinuousNumUp);
        getRequest().request(HttpUrls.API_Submit_Formula, HttpRequest.Method.POST, request, lifecycle, callback);
    }

}
