package com.hfad.url;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hfad.url.itity.GetVotingResponse;
import com.hfad.url.itity.Voting;
import com.hfad.url.model.DataAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RetrieveFeedTask retrieveFeedTask = new RetrieveFeedTask();
        retrieveFeedTask.execute();

//        ArrayList<Voting> list = new ArrayList<>();
//        list.add(new Voting("1"));
//        list.add(new Voting("2"));
        //getVotingResponse.data = list;
        recyclerView = findViewById(R.id.recycler_view1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter(this);
//        adapter.setVoitings(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this,Settings.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class RetrieveFeedTask extends AsyncTask<String, Void, GetVotingResponse> {

        private Exception exception;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected GetVotingResponse doInBackground(String... strings) {
            HttpURLConnection myURL = null;
            String request = "http://172.31.255.165/test/hs/api/";
            String in = "";
            JSONObject json;
            GetVotingResponse getVotingResponse = null;
            try {
                URL url = new URL(request);
                myURL = (HttpURLConnection) url.openConnection();
                myURL.setRequestMethod("POST");
                myURL.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                String str =  "{" +
                        "\"command\":" + "\"command_data_to_list\"" +
                        ", \"str_Data_In\":" + "\"\"" +
                        '}';
                byte[] outputInBytes = str.getBytes(StandardCharsets.UTF_8);
                String userCredentials = "guest:guest";
                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

                myURL.setRequestProperty ("Authorization", basicAuth);
                myURL.connect();
                OutputStream os = myURL.getOutputStream();
                os.write( outputInBytes );
                os.close();

                myURL.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(myURL.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                json = new JSONObject(sb.toString());
                in = json.getString("response");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                getVotingResponse = gson.fromJson(in, GetVotingResponse.class);
            } catch (MalformedURLException e){

            } catch (IOException e2){
                String error2 = e2.toString();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return getVotingResponse;
        }

        @Override
        protected void onPostExecute(GetVotingResponse in) {
            super.onPostExecute(in);
            adapter.setVoitings(in.data);
//            adapter.setVoitings(list);
            adapter.notifyDataSetChanged();
            //adapter = new DataAdapter();
            //recyclerView.setAdapter(adapter);
            //recyclerView.notify();
        }
    }
}
