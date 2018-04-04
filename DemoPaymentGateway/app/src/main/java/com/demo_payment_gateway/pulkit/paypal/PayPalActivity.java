package com.demo_payment_gateway.pulkit.paypal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo_payment_gateway.pulkit.R;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONObject;

import java.math.BigDecimal;

public class PayPalActivity extends AppCompatActivity {

    Button btnpay;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(Config.ENVIRONMENT_NO_NETWORK);
//            .environment(Config.PAYPAL_ENVIRONMENT_SANDBOX)
//            .clientId(Config.PAYPAL_CLIENT_ID)
//            .merchantName(Config.MERCHANT_NAME);
//            .merchantPrivacyPolicyUri()
//            .merchantUserAgreementUri();

    PayPalPayment thingToBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        findIds();
        startPaypalService();
        init();
    }

    private void findIds() {

        btnpay = findViewById(R.id.btnpay);
    }


    private void startPaypalService() {

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    private void init() {

        btnpay.setOnClickListener(new View.OnClickListener() {

            BigDecimal totalPayment = new BigDecimal(100);

            @Override
            public void onClick(View v) {
                thingToBuy = new PayPalPayment(totalPayment, Config.DEFAULT_CURRENCY, Config.MERCHANT_NAME,
                        PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(PayPalActivity.this, PaymentActivity.class);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                startActivityForResult(intent, Config.REQUEST_CODE_PAYMENT);
            }
        });
    }

    final String message = "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. " +
            "Please see the docs.";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                paymentConfirmation(data);

            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out.println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }

        } else if (requestCode == Config.REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                paymentAuthorization(data);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "FuturePaymentExample: The user canceled.", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void paymentConfirmation(Intent data) {

        PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
        if (confirm != null) {
            try {
                JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString());

                String paymentId = jsonObject.getJSONObject("response").getString("id");

                System.out.println(confirm.toJSONObject().toString(4));
                System.out.println(confirm.getPayment().toJSONObject().toString(4));

                Toast.makeText(getApplicationContext(), "Order placed", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void paymentAuthorization(Intent data) {
        PayPalAuthorization auth = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
        if (auth != null) {
            try {
                System.out.println(auth.toJSONObject().toString(4));

                String authorization_code = auth.getAuthorizationCode();
                System.out.println("authorization_code:" + authorization_code);

                sendAuthorizationToServer(auth);

                Toast.makeText(getApplicationContext(),
                        "Future Payment code received from PayPal", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization auth) {

    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
