package com.bytedance.android.live.pipo.pbl6_test.billing;

import com.android.billingclient.api.BillingResult;

/**
 * Created by NatalieWong on 23/5/24
 *
 * @author nat.wong@bytedance.com
 */
public interface BillingSetUpListener {

    void onSetUpSuccess(BillingResult result);

    void onSetUpFailed(BillingResult result);


}