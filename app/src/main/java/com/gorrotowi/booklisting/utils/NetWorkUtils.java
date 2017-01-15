package com.gorrotowi.booklisting.utils;

import android.util.Log;

import com.gorrotowi.booklisting.entitys.ItemBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gorro on 14/01/17.
 */

public class NetWorkUtils {

    private static final String TAG = NetWorkUtils.class.getSimpleName();

    public NetWorkUtils() {
    }

    public static List<ItemBook> fetchBooksData(String requestUrl) {
        URL url = createUrl(requestUrl);

        JSONObject response = null;

        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getBookList(response);
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Building URL", e);
        }
        return url;
    }

    private static JSONObject makeHttpRequest(URL url) throws IOException {
        JSONObject jsonResponse = null;
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Problem getting books info", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static JSONObject readFromStream(InputStream inputStream) throws IOException, JSONException {
        StringBuilder outPutString = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                outPutString.append(line);
                line = reader.readLine();
            }
        }
        return new JSONObject(outPutString.toString());
    }

    private static List<ItemBook> getBookList(JSONObject response) {

        List<ItemBook> itemBooks = new ArrayList<>();
        try {
            JSONArray items = response.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject currentJson = items.getJSONObject(i);
                JSONObject volumeInfoJson = currentJson.getJSONObject("volumeInfo");
                String urlImg = volumeInfoJson.getJSONObject("imageLinks").getString("smallThumbnail");
                String title = volumeInfoJson.getString("title");
                String year = volumeInfoJson.getString("publishedDate");
                int pCount = volumeInfoJson.getInt("pageCount");
                String urlBook = currentJson.getString("selfLink");
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < volumeInfoJson.getJSONArray("authors").length(); j++) {
                    stringBuilder.append(volumeInfoJson.getJSONArray("authors").get(j));
                    if (j != volumeInfoJson.getJSONArray("authors").length()-1) {
                        stringBuilder.append(" | ");
                    }
                }

                ItemBook itemBook = new ItemBook(urlImg, title, stringBuilder.toString(), year, pCount, urlBook);
                itemBooks.add(itemBook);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing book JSON", e);
        }
        return itemBooks;
    }
}
