package com.bytedance.android.live.pipo.pbl6_test.billing;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by NatalieWong on 23/5/24
 *
 * @author nat.wong@bytedance.com
 */
public class GoogleBilling5Service implements PurchasesUpdatedListener {

    private AtomicBoolean mIsServiceConnected = new AtomicBoolean(false);
    private AtomicBoolean mIsServiceConnecting = new AtomicBoolean(false);
    private CopyOnWriteArraySet<BillingSetUpListener> mBillingSetUpListeners = new CopyOnWriteArraySet<>();


    private BillingClient mBillingClient;
    private static volatile GoogleBilling5Service iGoogleBillingService;

    public static GoogleBilling5Service getInstance() {

        if (iGoogleBillingService == null) {
            synchronized (GoogleBilling5Service.class) {
                if (iGoogleBillingService == null) {
                    iGoogleBillingService =
                            new GoogleBilling5Service();
                }
            }
        }
        return iGoogleBillingService;
    }

    /**
     * 与google建联的Listener
     */
    private BillingClientStateListener mBillingClientStateListener = new BillingClientStateListener() {
        @Override
        public void onBillingSetupFinished(BillingResult billingResult) {
            String channelStr = "billingResult is null!";
            if (billingResult != null) {
                channelStr = "result code is " + billingResult.getResponseCode() + " result message is " +
                        billingResult.getDebugMessage();
            }

            mIsServiceConnecting.set(false);
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                mIsServiceConnected.set(true);
                //PaymentServiceManager.get().getGoogleIapExternalService().setBillingVersion(Constants.KEY_BILLING_FIVE);
                for (BillingSetUpListener billingSetUpListener : mBillingSetUpListeners) {
                    billingSetUpListener.onSetUpSuccess(billingResult);
                }
                mBillingSetUpListeners.clear();
            } else {
                mIsServiceConnected.set(false);
                //PaymentServiceManager.get().getGoogleIapExternalService().setBillingVersion(Constants.KEY_BILLING_UNAVAILABLE);
                for (BillingSetUpListener billingSetUpListener : mBillingSetUpListeners) {
                    billingSetUpListener.onSetUpFailed(billingResult);
                }
                mBillingSetUpListeners.clear();
            }
        }

        @Override
        public void onBillingServiceDisconnected() {
            mIsServiceConnecting.set(false);
            mIsServiceConnected.set(false);
            mIsServiceConnected.set(false);
        }
    };


    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }

    public void connectWithGoogleService(BillingSetUpListener billingSetUpListener) {
        if (mIsServiceConnecting.get()) {
            mBillingSetUpListeners.add(billingSetUpListener);
            return;
        }
        if (mIsServiceConnected.get()) {
            billingSetUpListener.onSetUpSuccess(null);
        } else {
            // If billing service was disconnected, we try to reconnect 1 time.
            // (feel free to introduce your retry policy here).

            mBillingSetUpListeners.add(billingSetUpListener);
            try {
                if (mIsServiceConnecting.getAndSet(true)) {
                    return;
                }
                mBillingClient.startConnection(mBillingClientStateListener);
            } catch (Throwable e) {
            }
        }
    }

    public Boolean checkBillingFeature() {
        if (mBillingClient == null) {
            return false;
        }
        Log.d("JobInfoScheduler", "isFeatureSupported");
        int responseCode = mBillingClient.isFeatureSupported(BillingClient.FeatureType.PRODUCT_DETAILS).getResponseCode();
        return responseCode == BillingClient.BillingResponseCode.OK;
    }

    public void init(Context context, InitListener initListener) {
        if (mBillingClient == null) {
            mBillingClient = BillingClient.newBuilder(context)
                    .setListener(this).enablePendingPurchases().build();
        }

        connectWithGoogleService(new BillingSetUpListener() {
            @Override
            public void onSetUpSuccess(BillingResult result) {
                initListener.onInitCallback(result);
            }

            @Override
            public void onSetUpFailed(BillingResult result) {
                initListener.onInitCallback(result);
            }
        });
    }
}
