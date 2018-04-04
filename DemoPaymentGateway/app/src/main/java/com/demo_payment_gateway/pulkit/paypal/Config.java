package com.demo_payment_gateway.pulkit.paypal;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

/**
 * Created by pulkit on 30/3/18.
 */

public class Config {

    // PayPal app configuration
    public static final String CONFIG_CLIENT_ID = "client id of sandbox";
    public static final String PAYPAL_CLIENT_ID = "ATc1IeQ60L7Q08YuEHV140gMX8FK52X7NiYg5-K-4uBaGSHq7OHSw0QuF-E1LToxwSHxudb1bRvop6ky";
    public static final String PAYPAL_CLIENT_SECRET = "";

    /*SandBox for test*/
    public static final String PAYPAL_ENVIRONMENT_SANDBOX = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    /*Production for real*/
    public static final String PAYPAL_ENVIRONMENT_PRODUCTION = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    /*Testing for No_Network*/
    public static final String ENVIRONMENT_NO_NETWORK = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    public static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
    public static final String DEFAULT_CURRENCY = "USD";
    public static final String MERCHANT_NAME = "Pulkit Aggarwal";

    // PayPal server urls
    public static final String URL_PRODUCTS = "http://192.168.0.103/PayPalServer/v1/products";
    public static final String URL_VERIFY_PAYMENT = "http://192.168.0.103/PayPalServer/v1/verifyPayment";

    public static final int REQUEST_CODE_PAYMENT = 1;
    public static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

}