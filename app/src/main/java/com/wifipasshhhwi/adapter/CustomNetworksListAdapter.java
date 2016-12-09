package com.wifipasshhhwi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wifipasshhhwi.R;

import java.util.List;
import java.util.Random;


public class CustomNetworksListAdapter extends ArrayAdapter<String> {

    private Activity mContext;
    private List<String> mList;
    private static final int RESOURCE_LAYOUT = R.layout.list_element;

    private static final Random RANDOM = new Random();
    private static final String[] SECURITY_ARRAY =
            new String[]{"WEP", "WPA", "WPA2", "WPA-PSK", "WPA2-PSK", "WPA-EAP", "WPA2-EAP"};

    public CustomNetworksListAdapter(Activity context, List<String> results) {
        super(context, RESOURCE_LAYOUT, results);
        mContext = context;
        mList = results;
    }

    private class ViewHolder {
        private TextView mNetworkName;
        private TextView mAuthType;
        private ImageView mSignalLevel;
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            view = inflater.inflate(R.layout.list_element, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mNetworkName = (TextView) view.findViewById(R.id.networkName);
            viewHolder.mAuthType = (TextView) view.findViewById(R.id.authType);
            viewHolder.mSignalLevel = (ImageView) view.findViewById(R.id.signalLevel);

            view.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) view.getTag();

        String networkName = mList.get(position);
        String authType = SECURITY_ARRAY[RANDOM.nextInt(SECURITY_ARRAY.length)];

        int resId;
        int signalStrength = RANDOM.nextInt(10 - 5) + 5;
        if (signalStrength < 5) resId = R.drawable.wifi_signal_3;
        else resId = R.drawable.wifi_signal_4;

        holder.mNetworkName.setText(networkName);
        holder.mAuthType.setText(authType);
        holder.mSignalLevel.setImageResource(resId);

        return view;
    }
}
