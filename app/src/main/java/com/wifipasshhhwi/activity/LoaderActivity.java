package com.wifipasshhhwi.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.tapjoy.TJActionRequest;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJEarnedCurrencyListener;
import com.tapjoy.TJError;
import com.tapjoy.TJGetCurrencyBalanceListener;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.Tapjoy;

import com.tapjoy.TapjoyConnectFlag;
import com.wifipasshhhwi.R;

import java.util.Hashtable;

public class LoaderActivity extends AppCompatActivity implements TJPlacementListener, TJGetCurrencyBalanceListener {

    private AlertDialog.Builder mOfferDialog;
    private ProgressBar mProgressBar;
    private static final String LOG_TAG = "LoaderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        mProgressBar = (ProgressBar) findViewById(R.id.progressLoader);

        setupDialog();
        startLoader();
    }

    private void setupDialog() {
        mOfferDialog = new AlertDialog.Builder(LoaderActivity.this);
        mOfferDialog.setTitle("Get WiFI Password");
        mOfferDialog.setMessage("Please download one of sponsor’s app and you will get the password  to WiFi network.");
        mOfferDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                connectTapjoy();
            }
        });

        mOfferDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mOfferDialog.show();
            }
        });
    }

    private void startLoader() {
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                mProgressBar.setProgress(values[0]);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    for (int i = 0; i < 101; i++) {
                        Thread.sleep(70);
                        publishProgress(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                mOfferDialog.show();
            }
        }.execute();
    }

    private void connectTapjoy() {
        Hashtable<String, Object> connectFlags = new Hashtable<>();
		connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");

        Tapjoy.connect(
                this,
                "GvP3r-BrQMm1D3U9WEyjuQECohEiRTgF8himW8rWIwuDW4pPDw2cRKav2rWD",
                connectFlags,
                new TJConnectListener() {
                    @Override
                    public void onConnectSuccess() {
                        showOffers();
                        Log.d(LOG_TAG, "ConnectSuccess");
                    }

                    @Override
                    public void onConnectFailure() {
                        Log.d(LOG_TAG, "ConnectFailure");
                    }
                });

        Tapjoy.setEarnedCurrencyListener(new TJEarnedCurrencyListener() {
            @Override
            public void onEarnedCurrency(String currencyName, int amount) {
                Log.d(LOG_TAG, "EarnedCurrency " + amount);

                LoaderActivity.this.setResult(RESULT_OK);
                LoaderActivity.this.finish();
            }
        });

    }

    private void showOffers() {
        Log.d(LOG_TAG, "showOffers");
        TJPlacement mPlacement = Tapjoy.getPlacement("Offerwall", this);
        mPlacement.requestContent();
    }

    @Override
    public void onRequestSuccess(TJPlacement tjPlacement) {
        Log.d(LOG_TAG, "RequestSuccess");
    }

    @Override
    public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {
        Log.d(LOG_TAG, "RequestFailure");
    }

    @Override
    public void onContentReady(TJPlacement tjPlacement) {
        Log.d(LOG_TAG, "ContentReady");
        tjPlacement.showContent();
    }

    @Override
    public void onContentShow(TJPlacement tjPlacement) {
        Log.d(LOG_TAG, "ContentShow");
    }

    @Override
    public void onContentDismiss(TJPlacement tjPlacement) {
        Log.d(LOG_TAG, "ContentDismiss");
        Tapjoy.getCurrencyBalance(this);
    }

    @Override
    public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {
        Log.d(LOG_TAG, "PurchaseRequest");
    }

    @Override
    public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {
        Log.d(LOG_TAG, "RewardRequest");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tapjoy.getCurrencyBalance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Tapjoy.onActivityStart(this);
    }

    @Override
    protected void onStop() {
        Tapjoy.onActivityStop(this);
        super.onStop();
    }

    @Override
    public void onGetCurrencyBalanceResponse(String s, int i) {
        Log.d(LOG_TAG, "GetCurrencyBalanceResponse " + s + " : " + i);
        mOfferDialog.show();
    }

    @Override
    public void onGetCurrencyBalanceResponseFailure(String s) {
        Log.d(LOG_TAG, "GetCurrencyBalanceResponseFailure " + s);
    }
}
