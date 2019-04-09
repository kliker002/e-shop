package ru.kliker02.practice.practice;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;
//тултипы, navdrawer; refresh layout;
public class MainApp extends AppCompatActivity {
    ImageView img, iv;
    RelativeLayout rl;
    Integer checkOut = 0;
    TextView responsetv;
    LinearLayout lr;
    final HashMap<String, Integer> purchases = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_app);
        img = (ImageView)findViewById(R.id.img);
        lr = (LinearLayout)findViewById(R.id.linl);

        getImagesFromServer();

    }
    public int getCostId(int id){
        Map <Integer, Integer> hashmap = new HashMap<Integer, Integer>();
        hashmap.put(1,12);
        hashmap.put(2,17);
        hashmap.put(3,4);
        return hashmap.get(id);
    }

    public void onClickCheckOut(View v){
        AlertDialog.Builder checkout_bilder = new AlertDialog.Builder(MainApp.this);
        checkout_bilder.setMessage("Your bill is: $" + checkOut).setCancelable(false).setPositiveButton("Pay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (purchases.isEmpty()){
                    Toast.makeText(getApplicationContext(), "You chose nothing", Toast.LENGTH_SHORT).show();
                }else{
                    ArrayList<String> products = new ArrayList<String>();
                    ArrayList<String> hmuch= new ArrayList<String>();
                    ArrayList<String> cost = new ArrayList<String>();// получаем по айдишнику цену;
                    // реализация здесь пока не предусмотрена;
                    for (int i = 0; i < purchases.size(); i++){
                        products.add(purchases.keySet().toArray()[i].toString());
                    }

                    for (int i = 0; i < purchases.size(); i++){
                        hmuch.add(purchases.get(products.get(i)).toString());
                    }

                    Intent intent = new Intent(getApplicationContext(), Checkout.class);
                    intent.putExtra("products", products);
                    intent.putExtra("hmuch", hmuch);
                    intent.putExtra("total_price", checkOut);
//                intent.putExtra("hmuch", hmuch); при реализации парсинга данных с бд; пока не предусмотрено
                    startActivity(intent);
                    // закончить переход в другую активити с оплатой
                }

            }
        }).setNegativeButton("I will make purchases", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog checkout = checkout_bilder.create();
        checkout.setTitle("CheckOut");
        checkout.show();
    }

    public void onClickDiscount(View v){
        Intent intent  = new Intent(getApplicationContext(), Discount.class);
        startActivity(intent);

    }
    public void getImagesFromServer(){
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://utc.rp5.local/wduck/testImages.php?v=4";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray preview_arr = response.getJSONObject("response").getJSONObject("gallery").getJSONArray("day");
                    String url_img = "";
                    for (int i = 0; i < 4;i++){
                        ImageView iv = new ImageView(getApplicationContext());

                        url_img = preview_arr.optString(i, null);

                        Picasso
                                .with(getApplicationContext())
                                .load(url_img)
                                .transform(new CropSquareTransformation())
                                .into(iv);// парсим картинку и обрезаем в квадрат

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        iv.setLayoutParams(lp);
                        lr.addView(iv);

                        Button buy = new Button(getApplicationContext());
                        buy.setText("Buy for: $" + i);

                        final int finalI = i;
                        buy.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                checkOut += finalI;

                                if (purchases.get("item" + finalI) != null) {
                                    purchases.put("item" + finalI, purchases.get("item" + finalI) + 1);
                                }else{
                                    purchases.put("item"+finalI, 1);
                                }
                                Toast.makeText(getApplicationContext(),"Bill: $" + checkOut + "; You bought - " + purchases.get("item"+finalI), Toast.LENGTH_SHORT).show();
                            }
                        });
                        buy.setBackgroundResource(R.drawable.buy_button);
                        buy.setTextSize(20);
                        buy.setTextColor(Color.WHITE);
                        lr.addView(buy);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsObjRequest);

    }




}
