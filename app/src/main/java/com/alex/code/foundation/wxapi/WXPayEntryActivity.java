package com.alex.code.foundation.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/22.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, AppConstants.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode +" errStr: " +resp.errStr);

	/*	if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string. );
			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			builder.show();
		}*/
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == BaseResp.ErrCode.ERR_OK){
                EventBus.getDefault().post(new MessageEvent(EventCons.WX_PAY_SUCCESS));
            }else {
                if (resp.errCode == BaseResp.ErrCode.ERR_COMM){
                    Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                }else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL){
                    Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
                }else if (resp.errCode == BaseResp.ErrCode.ERR_SENT_FAILED){
                    Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
                }
                EventBus.getDefault().post(new MessageEvent(EventCons.WX_PAY_FAIL));
            }
        }
        this.finish();
    }
}
