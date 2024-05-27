package com.bytedance.android.live.pipo.pbl6_test.billing;

import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingResult;

/**
 * Created by NatalieWong on 23/5/24
 *
 * @author nat.wong@bytedance.com
 */
public interface InitListener {
    void onInitCallback(@Nullable BillingResult iapResult);
}
