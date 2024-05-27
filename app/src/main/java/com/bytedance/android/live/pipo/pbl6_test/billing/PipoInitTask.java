package com.bytedance.android.live.pipo.pbl6_test.billing;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.bytedance.android.live.pipo.pbl6_test.utils.AppContextManager;

/**
 * Created by NatalieWong on 23/5/24
 *
 * @author nat.wong@bytedance.com
 */
public class PipoInitTask {

    String LogTag = "JobInfoScheduler";

    public void run(Context context) {
        Log.d("JobInfoScheduler", "LivePipoInitTask run task");
        initBillingService(context);
    }

    void initBillingService(Context c){
        InitListener listener = new InitListener() {
            @Override
            public void onInitCallback(@Nullable BillingResult iapResult) {
                if(iapResult != null && iapResult.getResponseCode()== BillingClient.BillingResponseCode.OK){
                    if(GoogleBilling5Service.getInstance().checkBillingFeature()) {
                        //complete Billing 5 Connection
                    } else {
                        //downgrade to Billing 3 API
                    }
                }
            }
        };
        //Context c = AppContextManager.INSTANCE.getApplicationContext();
        Log.d(LogTag, "PipoInitTask: packageName "+c.getPackageName());
        GoogleBilling5Service.getInstance().init(c, listener);
    }
}
