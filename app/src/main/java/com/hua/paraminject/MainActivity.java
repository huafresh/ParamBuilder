package com.hua.paraminject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

//
//        ParamBuilder.startMainActivity()
//                .isLogin(false)
//                .start(this);

        ParamBuilder.startMainActivity()
                .start(this);
        ParamBuilder.newTest2()
                .age2("2")
                .create();

        MyFragment2 myFragment2 = ParamBuilder.newMyFragment2()
                .create();

    }
}
