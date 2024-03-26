package com.example.movie_list_assessment;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private boolean loading = true;

    private int past, visible, total;

   private LinearLayoutManager mLayoutManager;


    private static final String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=d73a98b460a5b34b34d604571c1c64bf";



    List<MovieModelClass> movieList;

    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main);
//
//        swipeRefreshLayout.setOnRefreshListener(this);
//        header = findViewById(R.id.header);


//        Glide.with(this)
//                .load(R.drawable.header)
//                .into(header);

        GetData getData = new GetData();
        getData.execute();

    }

    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Page refreshed!", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }

    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String current=" ";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try{
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1){
                        current += (char) data;
                        data = isr.read();

                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (urlConnection !=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i = 0; i<jsonArray.length(); i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        //Declaration of Strings

                        MovieModelClass model = new MovieModelClass();
                        model.setId(jsonObject1.getString("title"));
                        model.setName(jsonObject1.getString("overview"));
                        model.setImage(jsonObject1.getString("poster_path"));
                        movieList.add(model);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);
        }
    }
    private void PutDataIntoRecyclerView (List<MovieModelClass> movieList){

        Adapter adapter = new Adapter(this, movieList);


        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                RearrangeItems();
            }
        });
        setupPagination();
    }

    private void RearrangeItems() {
        Collections.shuffle(movieList, new Random(System.currentTimeMillis()));
        Adapter adapter = new Adapter(this,movieList);
        recyclerView.setAdapter(adapter);
    }


    private void setupPagination() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {


                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if(dy > 0){

                    visible = mLayoutManager.getChildCount();
                    total = mLayoutManager.getItemCount();
                    past = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading){
                        if((visible + past) >= total){
                            loading = false;
//                            Toast.makeText(MainActivity.this,
//                                    "This is the last popular movie", Toast.LENGTH_SHORT).show();
                            PutDataIntoRecyclerView(movieList);
                          loading = true;
                        }

                    }
                }
//                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


}