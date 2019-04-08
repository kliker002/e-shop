package ru.kliker02.practice.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;
import static ru.kliker02.practice.practice.R.id.lrl;

//Название товаров передаётся в Extra, перед которым парсится из БД, как и цена
//Парсинг цены не реализован
public class Checkout extends AppCompatActivity {

    Toolbar toolbar;
    Button cancel;
    ListView checkout_list;
    LinearLayout lr;
    TextView total;

    ArrayList<String> products, hmuch;
    Integer total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        total = new TextView(getApplicationContext());
        cancel = (Button) findViewById(R.id.cancel);
        checkout_list = (ListView) findViewById(R.id.checkout_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lr = (LinearLayout) findViewById(R.id.lrl);

        toolbar.setTitle("Checkout");

        Intent intent = getIntent();
        products = (ArrayList<String>) intent.getSerializableExtra("products");
        hmuch = (ArrayList<String>) intent.getSerializableExtra("hmuch");
        total_price = (Integer) intent.getSerializableExtra("total_price");


        getDisplayListView(checkout_list, products, hmuch);

    }

    public void OnClickCancel(View v) {

        finish();

    }

    public void getDisplayListView(ListView checkout_list, ArrayList<String> products, ArrayList<String> hmuch) {
        ArrayList<String> sameValue = new ArrayList<String>();
        for (int i = 0; i < products.size(); i++) {
            sameValue.add("Product: " + products.get(i) + "; " + " Quantity: " + hmuch.get(i));
        }
        setTVTotalPrice(total, total_price);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sameValue);

        checkout_list.setAdapter(adapter);
    }

    public void setTVTotalPrice(TextView total_price, float price) {
        SharedPreferences discount = getSharedPreferences("sharedpref", Context.MODE_PRIVATE);
        price = price - price * (discount.getInt("discount", 1) / 100.0F);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        total_price.setLayoutParams(lp);
        total_price.setText("Total price is $" + price);
        total_price.setTextSize(18);
        total_price.setBackgroundColor(Color.parseColor("#ddd8ff"));

        lr.addView(total_price);


    }

    public void OnclickPay(View v) {
        Toast.makeText(getApplicationContext(), "Nothing yet", Toast.LENGTH_SHORT).show();
    }


}
