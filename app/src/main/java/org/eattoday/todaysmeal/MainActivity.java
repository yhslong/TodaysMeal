package org.eattoday.todaysmeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액티비티 전환
        Button button1 = (Button) findViewById(R.id.longinButton);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  Toast.makeText(getApplicationContext(), "액티비티 전환", Toast.LENGTH_LONG).show();

                // 액티비티 전환 코드
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(intent);
            }

        });

    }

}
