package com.demo_payment_gateway.pulkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        init();
    }

    private void findIds() {

        btnAddToCart = findViewById(R.id.btnAddToCart);
    }


    private void init() {

        btnAddToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
}
