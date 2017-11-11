package org.eattoday.todaysmeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class PostDetailActivity extends AppCompatActivity {

    String id;
    String alias;
    String image_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // 다른 Activity 에서 전달해준 Intent 를 받는다
        TextView int_alias = (TextView) findViewById(R.id.alias);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        alias = intent.getStringExtra("alias");
       // Toast.makeText(getApplicationContext(),alias+"님이 입장하셨습니다.", Toast.LENGTH_LONG).show();
        int_alias.setText(alias);
        int_alias.setFocusable(false);
        int_alias.setClickable(false);

//        final RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
//        Button b = (Button)findViewById(R.id.button1);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int radio_id = rg.getCheckedRadioButtonId();
//                Toast.makeText(getApplicationContext(),radio_id +"이미지번호", Toast.LENGTH_LONG).show();
//                //getCheckedRadioButtonId() 의 리턴값은 선택된 RadioButton 의 id 값.
//                RadioButton rb = (RadioButton) findViewById(radio_id);
//                Toast.makeText(getApplicationContext(),rb.getText().toString()+"이미지번호", Toast.LENGTH_LONG).show();
//                image_no  = rb.getText().toString();
//            //    tv.setText("결과: " + rb.getText().toString());
//            } // end onClick()
//        });  // end Listener

    }

    // 글씨기 등록
    public void postit(View view) {
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
        int radio_id = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(radio_id);

        EditText editText = (EditText)findViewById(R.id.editText);
        new Postit().execute(
                "http://172.16.2.17:52273/post",
                id.toString(),
                alias.toString(),
                rb.getText().toString(),
                editText.getText().toString());
    }

    class Postit extends AsyncTask<String,String,String> {
        ProgressDialog dialog = new ProgressDialog(PostDetailActivity.this);
        @Override
        protected String doInBackground(String... params) {
            StringBuilder output = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_real_id", params[1]);
                postDataParams.put("alias", params[2]);
                postDataParams.put("image_no", params[3]);
                postDataParams.put("message", params[4]);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true); conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while(true) {
                        line = reader.readLine();
                        if (line == null) break;
                        output.append(line);
                    }
                    reader.close();
                    conn.disconnect();
                }
            } catch (Exception e) { e.printStackTrace(); }
            return output.toString();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("게시판 등록 중...");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            try {
                JSONObject json = new JSONObject(s);
                if (json.getBoolean("result") == true) {//글씨기 성공
                    Toast.makeText(PostDetailActivity.this,
                            "게시 성공되었습니다. 감사합니다.",
                            Toast.LENGTH_SHORT).show();
                  //  Intent intent = new Intent(PostDetailActivity.this, LoginActivity.class);
                  //  startActivity(intent);
                  //  finish();
                } else {//로그인 실패
                    Toast.makeText(PostDetailActivity.this,
                            "게시 성공하지 못했습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
