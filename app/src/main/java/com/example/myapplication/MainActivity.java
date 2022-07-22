package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    /**
     * API KEY
     * 79437ae17dfd4a2dae17531caa28598f
     */

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.txt);

        getETHBalance();
    }


    private void getETHBalance() {

        /**
         * After creating a new project on infura.io, we get the url with api key
         */
        final Web3j web3jClient = Web3j.build(new HttpService("https://mainnet.infura.io/v3/79437ae17dfd4a2dae17531caa28598f"));


        /**
         * Take any eth address
         * Here , I put a eth address manually
         */
        final String ethAddress = "0xbB2Dae8C67574f56D6Ea3155d1b56E304d3690B3";


        try {
            final EthGetBalance balanceResponse = web3jClient.ethGetBalance(ethAddress, DefaultBlockParameter.valueOf("latest")).sendAsync()
                    .get(10, TimeUnit.SECONDS);

            final BigInteger unscaledBalance = balanceResponse.getBalance();

            // textView.setText(String.valueOf(unscaledBalance));

            BigDecimal scaledBalance = new BigDecimal(unscaledBalance)
                    .divide(new BigDecimal(1000000000000000000L), 18, RoundingMode.HALF_UP);

            textView.setText(scaledBalance + " " + getString(R.string.text_ether));

        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show();

            textView.setText(e.getMessage());
        }


    }
}