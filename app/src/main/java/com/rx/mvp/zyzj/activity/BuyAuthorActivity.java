package com.rx.mvp.zyzj.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.android.base.widget.NoScrollGridView;
import com.rx.mvp.zyzj.BuildConfig;
import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.adapter.BuyAuthorAdapter;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.entity.AuthResult;
import com.rx.mvp.zyzj.entity.PayResult;
import com.rx.mvp.zyzj.entity.resp.BuyAuthorEntity;
import com.rx.mvp.zyzj.entity.resp.OrderEntity;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;
import com.rx.mvp.zyzj.iface.IAuthInfoView;
import com.rx.mvp.zyzj.iface.IUserInfoView;
import com.rx.mvp.zyzj.presenter.AuthInfoPresenter;
import com.rx.mvp.zyzj.presenter.UserInfoPresenter;
import com.rx.mvp.zyzj.utils.OrderInfoUtil2_0;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.alipay.sdk.app.PayTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/29.
 */

public class BuyAuthorActivity extends BaseActivity implements View.OnClickListener, IAuthInfoView, IUserInfoView {
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.buy_gridview)
    NoScrollGridView gridView;
    @BindView(R.id.buy_ll_remind)
    LinearLayout llRemind;
    @BindView(R.id.buy_img_wechat)
    ImageView imgWeChat;
    @BindView(R.id.buy_img_zhi)
    ImageView imgZhi;

    private BuyAuthorAdapter adapter;
    private List<BuyAuthorEntity> listAuthors;

    private AuthInfoPresenter authInfoPresenter = new AuthInfoPresenter(this, this);
    private UserInfoPresenter userInfoPresenter = new UserInfoPresenter(this, this);

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2018082961190006";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088231484815240";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPPbOiZshKb991o2+7twZh1fKcmb8AtGc+P3dMixoo1LyO5ceATTyW8JbLT2aqPPly5BRf9GiPPpX602WDSFnEoAIcFSGz823M69osU3iyopShpO/NgMYjePvzN7pUAFIAbP19nodVRH7LGuP+kaTIaf3zODFlzeRxFFIf2LDorp7/RLe5S4v16XxQ0vSJl5lgVzMR4yt+T8cTC5o/hF6TeFQhnxWxHHwLTfC6t3XHbkqt4U3l2gyuP+8E367kSLltING8IWF57R0MYSf0QSyo9PLcIWczXMgu30FDjb6bs9470TzXavOVPDMuoSLdjBmBgaFlcERh8hBGjLafN3P/AgMBAAECggEAD0yv2zDPQ+QfIhmK3an27IxFlaR2WkTqoiN1Qp+485OFcF3qqTGWvyiDcsPQW26X1kW2R2l7oZE1w8zAkv0vWWkd0JIRP39IwxKOAx4HTk0/QuSzGGggwbkycBALvt0p5RG/IaPFIL5oLJl6sL4ts5penZPq8szDEbDx9KTYgIG9S1RoBixvnpWeNv/yXSphpSPcyaBvT1JhjkxtVncB2eUuSHaTpDl1JRvUNS2/pHwcngtD2PA7A3MwkiASxbNmSjiZLTy48TRV4YSNfLO2JbdegJczmgXfyPXyhigd06IhGRBpKSiWZhMFG+0TZAHCSAYggWF5t/dRCSf2NY6fgQKBgQDMYTD2dfu02LOplnb/K+PwsQQTAbSGJh5qvbYWVEyD2JsyY9nazz8Q36jOmCpYRxsKM2DrwIydC3cZ1zEXq7qYwExlOD31PLIF/LCVUmlytuqyAX8XNeeFExQDR3V310UxSbU3Eyllj7gwpblu9frylUU0pZ2JJLAqIzeSsJALOQKBgQCza2ZuFAt1k81+AYV0J7HybiwkiynuNCUKY3OEZfB0lum0ssR+qAq3lYUx5Z/0wI250Rtgh3UUzduz7Go+EWrfhMnEXaNqj0AY5S2JhD/HDxhvX9aHiKsHd3OixZR5PrqSNHfO/5WJ+Xfl5r2Y8sn5N2l2IS1QsMkHxDXbwgGg9wKBgE9kSdLTjg14SIJOMrd44ziyarS/atLZp+MrArq10Gs/DzqJ0yzOaN8zIq3uKjTUmkpiHvo1d+nhdEiTcTyStw9zU7nWGStUgosU17uJofCv4Yd4UVhA1ki5WoLTlWwSMYyt5zkXtnL/Wzt6hQf3gZ4OIi4LT6DgoZYczA+tiqbxAoGBAJ1xs9MJF/03lG3sm9WQN+FLuMTgsgzsCU0SdH5MjjNhcvg0n6WdhLoRJW7h1/oJNtVGoEWW2LFG7HL5FUoeMrCub/g3S33o8xBlnA4+1VCy+HhTm4gX8C1/LyQ5Sc8Tstk13aTyN6F6HzLJxrr+4yok7BDTkFZsrBx6m+CaYJ+FAoGAPIPpMCChg+X+wnjTm/3xV+uAFYtHJoWs3peMzRGHZ0g8QCNYOA0D9+BmbE69XpTBixT55gR7qoaoYK5S6Xe8Zf0k/moNet+bn+I3TJiymX8qGrZKqJG0B7FMIdt0P/y+vBxsicZbBnKjnw+IueqKw4W2vMD7ANl6zoPnt8IkjWc=";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private final String alipayType = "2";
    private final String wechatType = "3";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = BuildConfig.VERSION_NAME;
    private String productName = "";
    private String orderNo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_buy_author;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText("购买授权");

        imgBack.setOnClickListener(this);
        imgWeChat.setOnClickListener(this);
        imgZhi.setOnClickListener(this);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if(adapter.getList().get(position).isEnable()){
                adapter.setBuyAuthorEntity(adapter.getList().get(position));
            }else{
                showToast("不能续约此版本，请重新选择");
            }

        });
    }

    @Override
    protected void initData() {
        adapter = new BuyAuthorAdapter(this);
        gridView.setAdapter(adapter);

        authInfoPresenter.getAuthInfo();

    }

    private void setDatas() {
        //默认选中 可购买的产品
        for(BuyAuthorEntity eable : listAuthors){
            if(eable.isEnable()){
                adapter.setBuyAuthorEntity(eable);
            }
            break;
        }

        adapter.appendToList(listAuthors);

        llRemind.removeAllViews();
        for (BuyAuthorEntity entity : listAuthors) {
            if(StringUtils.isEmpty(entity.getRemark()))
                continue;
            View view = LayoutInflater.from(this).inflate(R.layout.item_buy_remind, null);
            TextView tvRemind = (TextView) view.findViewById(R.id.buy_tv_remindMsg);
            tvRemind.setText(entity.getRemark());
            llRemind.addView(view);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_left_photo:
                finish();
                break;
            case R.id.buy_img_wechat:
                if(orderNo != null)
                    authInfoPresenter.findOrder(orderNo);
//                showToast("微信支付" + adapter.getBuyAuthorEntity().getGoodsId());
                break;
            case R.id.buy_img_zhi: //支付宝支付
                if(adapter.getBuyAuthorEntity() != null){
                    productName = adapter.getBuyAuthorEntity().getGoodsName();
                    toCreateOrder(adapter.getBuyAuthorEntity().getGoodsId(), alipayType);
                }else{
                    showToast("请选择授权类型");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {
        showLoding();
    }

    @Override
    public void closeLoading() {
        disLoding();
    }

    @Override
    public void onError(int code, String msg) {
        sendFail(code, msg);
    }

    @Override
    public void getAuthInfoSuccess(List<BuyAuthorEntity> list) {
        if(list != null && list.size() > 0){
            listAuthors = list;
            setDatas();
        }

    }

    private void toCreateOrder(String goodsId, String payType){
        String lotteryType;
        if(ZyzjApplication.getApplication().getLotteryEntity() != null){
            lotteryType = ZyzjApplication.getApplication().getLotteryEntity().getAbbreviation();
        }else{
            lotteryType = "bjpk10";
        }
        authInfoPresenter.createOrder("1", goodsId, lotteryType, payType);
    }

    @Override
    public void createOrder(OrderEntity orderEntity) {
        if(orderEntity != null){
            if(alipayType.equals(orderEntity.getPayType())){//支付宝支付
                orderNo = orderEntity.getOrderNo();
                alipay(orderEntity.getMoney(), orderEntity.getOrderNo(), orderEntity.getCreateTime());
            }else if(wechatType.equals(orderEntity.getPayType())){//微信支付

            }
        }
    }

    @Override
    public void findOrder(OrderEntity orderEntity) {
        if(orderEntity != null){
            showToast(orderEntity.getOrderStatus());
        }
    }

    /**
     * 支付宝支付业务
     *
     */
    public void alipay(String money, String orderNo, String times) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, money, orderNo, times, productName);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(BuyAuthorActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     */
    public void authV2() {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(BuyAuthorActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(BuyAuthorActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        refreshUserInfo();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(BuyAuthorActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(BuyAuthorActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(BuyAuthorActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }

                default:
                    break;
            }

        };
    };

    private void refreshUserInfo(){
        userInfoPresenter.getUserInfo();
    }

    @Override
    public void logoutSuccess(String msg) {

    }

    @Override
    public void getUserInfo(UserInfoEntity entity) {
        finish();
    }
}
