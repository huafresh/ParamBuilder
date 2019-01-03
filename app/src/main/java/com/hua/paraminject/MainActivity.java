package com.hua.paraminject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hua.parambuilder_annotation.Builder;
import com.hua.parambuilder_annotation.Param;
import com.hua.parambuilder_core.ParamBuilder;

@Builder
public class MainActivity extends AppCompatActivity {

    @Param
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamBuilder.startMainActivity()
                        .start(MainActivity.this);
                ParamBuilder.newTest2()
                        .age2("2")
                        .create();

                MyFragment2 myFragment2 = ParamBuilder.newMyFragment2()
                        .create();
            }
        });

//
//        ParamBuilder.startMainActivity()
//                .isLogin(false)
//                .start(this);


    }
}
