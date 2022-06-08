package com.muhamadrifki.forex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingProgressBar;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private TextView audTextView,jpyTextView,idrTextView,eurTextView,aedTextView,gbpTextView,mxnTextView,phpTextView,sekTextView,usdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout1 = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout1);
        audTextView = (TextView) findViewById(R.id.audTextView);
        jpyTextView = (TextView) findViewById(R.id.jpyTextView);
        idrTextView = (TextView) findViewById(R.id.idrTextView);
        eurTextView = (TextView) findViewById(R.id.eurTextView);
        aedTextView = (TextView) findViewById(R.id.aedTextView);
        gbpTextView = (TextView) findViewById(R.id.gbpTextView);
        mxnTextView = (TextView) findViewById(R.id.mxnTextView);
        phpTextView = (TextView) findViewById(R.id.phpTextView);
        sekTextView = (TextView) findViewById(R.id.sekTextView);
        usdTextView = (TextView) findViewById(R.id.usdTextView);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        initSwipeRefreshLayout();
        initForex();

    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout1.setOnRefreshListener(()->{
            initForex();

            swipeRefreshLayout1.setRefreshing(false);
        });
    }
    public String formatNumber(Double number,String format){
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }
    private void initForex() {
        loadingProgressBar.setVisibility(TextView.VISIBLE);

        String url = "https://openexchangerates.org/api/latest.json?app_id=835235e2406141d685addeb8f5b9ad35";

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                RootModel rootModel = gson.fromJson(new String(responseBody),RootModel.class);
                RatesModel ratesModel = rootModel.getRatesModel();

                double aud = ratesModel.getIDR() / ratesModel.getAUD();
                double jpy = ratesModel.getIDR() / ratesModel.getJPY();
                double eur = ratesModel.getIDR() / ratesModel.getEUR();
                double aed = ratesModel.getIDR() / ratesModel.getAED();
                double gbp = ratesModel.getIDR() / ratesModel.getGBP();
                double mxn = ratesModel.getIDR() / ratesModel.getMXN();
                double php = ratesModel.getIDR() / ratesModel.getPHP();
                double sek = ratesModel.getIDR() / ratesModel.getSEK();
                double usd = ratesModel.getIDR() / ratesModel.getUSD();
                double idr = ratesModel.getIDR();

                audTextView.setText(formatNumber(aud,"###,##0.##"));
                jpyTextView.setText(formatNumber(aud,"###,##0.##"));
                eurTextView.setText(formatNumber(aud,"###,##0.##"));
                aedTextView.setText(formatNumber(aud,"###,##0.##"));
                gbpTextView.setText(formatNumber(aud,"###,##0.##"));
                mxnTextView.setText(formatNumber(aud,"###,##0.##"));
                phpTextView.setText(formatNumber(aud,"###,##0.##"));
                sekTextView.setText(formatNumber(aud,"###,##0.##"));
                usdTextView.setText(formatNumber(aud,"###,##0.##"));
                // idr ada masalah
                loadingProgressBar.setVisibility(TextView.INVISIBLE);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                loadingProgressBar.setVisibility(TextView.INVISIBLE);

            }
        });
    }

}