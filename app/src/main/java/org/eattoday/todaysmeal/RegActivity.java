package org.eattoday.todaysmeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {

    String id;
    String alias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        // 다른 Activity 에서 전달해준 Intent 를 받는다
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        alias = intent.getStringExtra("alias");
        Toast.makeText(getApplicationContext(),alias+"님이 입장하셨습니다.", Toast.LENGTH_LONG).show();



  /*      TextView tv_username = (TextView) findViewById(R.id.tv_username);
        TextView tv_birthady = (TextView) findViewById(R.id.tv_birthday);

        Intent intent = getIntent(); //이 액티비티를 부른 인텐트를 받는다.
        String username = intent.getStringExtra("USERNAME_KEY"); //"jizard"문자 받아옴
        int birthday = intent.getIntExtra("BIRTHDAY_KEY",0); //119 받아옴

        tv_username.setText(username);
        tv_birthday.setText(String.valueOf(birthday)); */



        Button button1 = (Button) findViewById(R.id.regist_bg);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(), "액티비티 전환", Toast.LENGTH_LONG).show();

                // 액티비티 전환 코드
                Intent intent = new Intent(getApplicationContext(), PostDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("alias", alias);
                startActivity(intent);
            }

        });

        Button button2 = (Button) findViewById(R.id.putup_bg);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(), "액티비티 전환", Toast.LENGTH_LONG).show();

                // 액티비티 전환 코드
                Intent intent = new Intent(getApplicationContext(), PutUpActivity.class);
                startActivity(intent);
            }

        });


    }
}
