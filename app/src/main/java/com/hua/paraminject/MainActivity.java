package com.hua.paraminject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hua.parambuilder_annotation.Builder;
import com.hua.parambuilder_annotation.Param;

@Builder
public class MainActivity extends AppCompatActivity {

    @Param
    boolean isLogin;
    private String name;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//
//        ParamBuilder.startMainActivity()
//                .isLogin(false)
//                .start(this);


    }


    public static Intent createIntent(Context context, boolean isLogin, String name, int age) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("isLogin", isLogin);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        return intent;
    }

    public void initIntentParams() {
        Intent intent = getIntent();
        isLogin = intent.getBooleanExtra("isLogin", false);
        name = intent.getStringExtra("name");
        age = intent.getIntExtra("age", 0);
    }
}
