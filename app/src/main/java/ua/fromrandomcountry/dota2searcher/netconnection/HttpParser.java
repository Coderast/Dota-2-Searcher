package ua.fromrandomcountry.dota2searcher.netconnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.os.AsyncTask;

import javax.net.ssl.HttpsURLConnection;

public class HttpParser extends AsyncTask<String, Void, String>{
    private Exception exception;
    private String taskDone = "";
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

    public String isDone() {
        return taskDone;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        taskDone = "A";
    }

    protected String doInBackground(String... urls) {
        if (urls.length != 1) {
            exception = new InvalidParameterException("Url should be only one!");
            return null;
        }
        try {
            URL urlObject = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

            connection.setRequestMethod("GET");

            InputStream stream = connection.getErrorStream();
            if (stream == null) {
                stream = connection.getInputStream();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(stream)
            );
            String inputLine;
            StringBuilder jsonSource = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                jsonSource.append(inputLine);
            }
            in.close();
            return jsonSource.toString();
        }
        catch (Exception e) {
            exception = e;
        }
        taskDone = "B";
        return null;
    }


    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (exception != null) {
            result = null;
        }
        taskDone = "C";
    }
}
