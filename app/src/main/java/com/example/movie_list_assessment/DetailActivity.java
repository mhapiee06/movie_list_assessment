package com.example.movie_list_assessment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);


        Button button = (Button) findViewById(R.id.buttonback);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageView = findViewById(R.id.imageView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView id_txt = findViewById(R.id.id_txt);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView name_txt = findViewById(R.id.name_txt);

        Bundle bundle = getIntent().getExtras();

        String mTitle = bundle.getString("title");
        String mOverview = bundle.getString("overview");
        String mPoster = bundle.getString("poster_path");


        Glide.with(DetailActivity.this).load(mPoster).into(imageView);
        id_txt.setText(mTitle);
        name_txt.setText(mOverview);

    }
}