package org.eattoday.todaysmeal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ListView listView = null;
    class Item{
        int image;
        String title;
        String text;

        Item(int image,String title,String text ) {
            this.image = image;
            this.title = title;
            this.text  = text;
        }
    }

    ArrayList<Item> itemList = new ArrayList<Item>();
    class ItemAdapter extends ArrayAdapter{
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                LayoutInflater layoutInflater =
                        (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = layoutInflater.inflate(R.layout.list_item,null);
            }
            ImageView imageView = (ImageView)convertView.findViewById((R.id.image));
            TextView text1View = (TextView)convertView.findViewById(R.id.title);
            TextView text2View = (TextView)convertView.findViewById(R.id.text);
            Item item = itemList.get(position);
            imageView.setImageResource(item.image);
            text1View.setText(item.title);
            text2View.setText(item.text);
            return convertView;
        }

        public  ItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects){
            super(context, resource, objects);
        }


    }

    ItemAdapter itemAdpater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        listView = (ListView)findViewById(R.id.listView);
        itemList.add(new Item(R.drawable.angry,"ID:Angry","오늘 A랑 싸워서 화가나요"));
        itemList.add(new Item(R.drawable.happy, "ID:Happy", "오늘 선물을 받아서 기분이 좋아요"));
        itemList.add(new Item(R.drawable.rough, "ID:Rough", "파이터 가기전.."));
        itemList.add(new Item(R.drawable.sad, "ID:Sad", "감기가 걸렸어요 .. ㅜㅜ"));
        itemList.add(new Item(R.drawable.timid, "ID:Timid", "오늘 실수를...ㅠㅠ"));
       /* String[] items ={"item1","item2","item3","item4","item5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PostActivity.this,android.R.layout.simple_list_item_1,items); */
        itemAdpater = new ItemAdapter(PostActivity.this, R.layout.list_item,
                itemList);
        listView.setAdapter(itemAdpater);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),PostDetailActivity.class);
                intent.putExtra("image",itemList.get(position).image);
                intent.putExtra("title",itemList.get(position).title);
                intent.putExtra("item",itemList.get(position).text);
                startActivity(intent);

            }
        });


    }
}
