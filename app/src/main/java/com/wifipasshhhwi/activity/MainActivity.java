package com.wifipasshhhwi.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tapjoy.Tapjoy;
import com.wifipasshhhwi.R;
import com.wifipasshhhwi.adapter.CustomNetworksListAdapter;
import com.wifipasshhhwi.adapter.NetworksListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    private WifiManager mWifiManager;
    private static final Random RANDOM = new Random();
    private static final int LOADER_CODE = 1;
    private static final char[] CHARS_ARRAY =
            "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789".toCharArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        initListComponents();
    }


    private void initListComponents() {
        listView = (ListView) findViewById(R.id.list);

        ArrayAdapter adapter;
        List<ScanResult> scanResults = mWifiManager.getScanResults();

        if (!scanResults.isEmpty()) adapter = new NetworksListAdapter(this, mWifiManager.getScanResults());
        else adapter = new CustomNetworksListAdapter(this, getCustomNetworks());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent loader = new Intent(getApplicationContext(), LoaderActivity.class);
                startActivityForResult(loader, LOADER_CODE);
            }
        });
    }

    private List<String> getCustomNetworks() {
        int networkCount = RANDOM.nextInt(9 - 5) + 5;
        List<String> result = new ArrayList<>(networkCount);

        for (int i = 0; i < networkCount; i++) {
            int nameLength = RANDOM.nextInt(9 - 5) + 5;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < nameLength; j++) {
                char c = CHARS_ARRAY[RANDOM.nextInt(CHARS_ARRAY.length)];
                sb.append(c);
            }
            result.add(sb.toString());
        }

        return result;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("LoaderActivity", requestCode + " " + resultCode + " " + RESULT_OK);
        if (resultCode == RESULT_OK) {
            AlertDialog.Builder successDialog = new AlertDialog.Builder(MainActivity.this);
            successDialog.setTitle("Success");
            successDialog.setMessage("Thank you for completing the offer. You will be connected in 5 minutes .");
            successDialog.setPositiveButton("OK", null);
            successDialog.show();
        }
    }
}
