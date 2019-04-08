package ru.kliker02.practice.practice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class Discount extends AppCompatActivity {

    RelativeLayout rl;
    LinearLayout lr;
    SharedPreferences settings;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        email = (EditText) findViewById(R.id.email);

        lr = (LinearLayout) findViewById(R.id.lrl);
        rl = (RelativeLayout) findViewById(R.id.rl);
        setSupportActionBar(toolbar);

        getImage();
    }
    public void getImage(){
        ImageView targetImageView = new ImageView(getApplicationContext());
        String internetUrl = "https://gde.ru/images/img_ru/474x354/7a/a5/7aa5f2dbebe9f15f24a3ffc8ef7265d8.jpg";

        Picasso
                .with(getApplicationContext())
                .load(internetUrl)
                .transform(new CropSquareTransformation())
                .into(targetImageView);
        targetImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        lr.addView(targetImageView);
    }
    public void onClickDiscount(View v){
        setDiscount();
    }
    public void setDiscount(){
        settings = getSharedPreferences("sharedpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = settings.edit();
        ed.putInt("discount", 10);
        ed.apply();
        Toast.makeText(getApplicationContext(), "Discount: " + settings.getInt("discount", 1) , Toast.LENGTH_SHORT).show();



    }

}
