package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_rxjava)
    protected Button rxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_rxjava)
    public void startRxActivity() {
        Toast.makeText(this, R.string.rx_start_toast, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RxMainActivity.class);
        startActivity(intent);
    }
}
