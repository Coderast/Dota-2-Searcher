package ua.fromrandomcountry.dota2searcher.netconnection;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.HttpsURLConnection;

public class HttpParser extends AsyncTask<String, Void, String> {
    public String getPageSource(String url) throws InterruptedException, ExecutionException, TimeoutException {
        /*this.execute(url);
        int deciMinutes = 0;
        for (;!taskDone;deciMinutes++) {
            if (deciMinutes > 300) {
                throw new TimeoutException("Timeout. Can't solve task more then 30 seconds.");
            }
            TimeUnit.MICROSECONDS.sleep(100);
        }*/
        return this.get();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... urls) {
        if (urls.length != 1) {
            throw new InvalidParameterException("Url should be only one!");
        }

        StringBuilder jsonSource = new StringBuilder();
        try {
            URL urlObject = new URL(urls[0]);
            HttpsURLConnection connection = (HttpsURLConnection) urlObject.openConnection();

            connection.setRequestMethod("GET");

            InputStream stream = connection.getErrorStream();
            if (stream == null) {
                stream = connection.getInputStream();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(stream)
            );
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                jsonSource.append(inputLine);
            }
            in.close();

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(urls[0]);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(jsonSource.toString());

            /*
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urls[0]);
            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent()
                    )
            );
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
            */
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jsonSource.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
