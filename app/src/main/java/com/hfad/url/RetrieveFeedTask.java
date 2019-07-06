package com.hfad.url;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hfad.url.itity.GetVotingResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
    }
}
