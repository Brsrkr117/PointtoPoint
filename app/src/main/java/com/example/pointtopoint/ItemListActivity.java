package com.example.pointtopoint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ItemListActivity extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Non Veg Small", "Non veg Medium", "Non Veg Large", "Veg Small", "Veg Medium","Veg Large"};
    String mDescription[] = {"60", "70", "80", "50", "60", "70"};
    int images[] = {R.drawable.shopping_cart,R.drawable.shopping_cart, R.drawable.shopping_cart, R.drawable.shopping_cart, R.drawable.shopping_cart, R.drawable.shopping_cart};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        listView = findViewById(R.id.listView);


        MyAdapter adapter = new MyAdapter(ItemListActivity.this, mTitle, mDescription, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Toast.makeText(ItemListActivity.this, "You have selected Non Veg Small", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OrderViewActivity.class);
                    Bundle bundle = new Bundle();
                    //bundle.putInt("position",0);
                    bundle.putString("title",mTitle[0]);
                    bundle.putString("description", mDescription[0]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[0]);
                    intent.putExtra("description", mDescription[0]);
                    intent.putExtra("position", ""+0);
                    startActivity(intent);


                }
                if (position ==  1) {
                    Toast.makeText(ItemListActivity.this, "You have selected Non veg Medium", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OrderViewActivity.class);
                    Bundle bundle = new Bundle();
                    //bundle.putInt("position",1);
                    bundle.putString("title",mTitle[1]);
                    bundle.putString("description", mDescription[1]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[1]);
                    intent.putExtra("description", mDescription[1]);
                    intent.putExtra("position", ""+1);
                    startActivity(intent);
                }
                if (position ==  2) {
                    Toast.makeText(ItemListActivity.this, "You have selected Non Veg Large", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OrderViewActivity.class);
                    Bundle bundle = new Bundle();
                    //bundle.putInt("position",2);
                    bundle.putString("title",mTitle[2]);
                    bundle.putString("description", mDescription[2]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[2]);
                    intent.putExtra("description", mDescription[2]);
                    intent.putExtra("position", ""+2);
                    startActivity(intent);
                }
                if (position ==  3) {
                    Toast.makeText(ItemListActivity.this, "You have selected Veg Small", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OrderViewActivity.class);
                    Bundle bundle = new Bundle();
                    //bundle.putInt("position",3);
                    bundle.putString("title",mTitle[3]);
                    bundle.putString("description", mDescription[3]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[3]);
                    intent.putExtra("description", mDescription[3]);
                    intent.putExtra("position", ""+3);
                    startActivity(intent);
                }
                if (position ==  4) {
                    Toast.makeText(ItemListActivity.this, "You have selected Veg Medium", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OrderViewActivity.class);
                    Bundle bundle = new Bundle();
                    //bundle.putInt("position",4);
                    bundle.putString("title",mTitle[4]);
                    bundle.putString("description", mDescription[4]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[4]);
                    intent.putExtra("description", mDescription[4]);
                    intent.putExtra("position", ""+4);
                    startActivity(intent);
                }
                if (position ==  5) {
                    Toast.makeText(ItemListActivity.this, "You have selected Veg Large", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OrderViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",mTitle[5]);
                    bundle.putString("description", mDescription[5]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[5]);
                    intent.putExtra("description", mDescription[5]);
                    intent.putExtra("position", ""+5);
                    startActivity(intent);
                }

            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];


        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.imagerow);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText("Rs" + rDescription[position]);
            return row;
        }
    }
}
