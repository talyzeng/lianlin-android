package wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
/*
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
    
    private IWXAPI api;
    
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    // 注册微信sdk
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, true);
        api.registerApp(Constant.APP_ID);
        api.handleIntent(getIntent(), this);
}

/**
*  微信发送请求到第三方应用时，会回调到该方法
*/
/*
    @Override
    public void onReq(BaseReq req) {
            
    }
/**
* 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
*/
/*
    @Override
    public void onResp(BaseResp resp) {
            String result = "分享失败！";
            switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK: // 分享成功
                    result = "分享成功！";
                    break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:// 取消分享
                    result = "取消分享！";
                    break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:// // 分享失败
                    result = "分享失败！";
                    break;
            default:
                    result = "分享失败！";
                    break;
            }
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            finish();
    }
    
}
*/

