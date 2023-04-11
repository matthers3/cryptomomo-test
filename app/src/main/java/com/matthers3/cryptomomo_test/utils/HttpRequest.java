package com.matthers3.cryptomomo_test.utils;

import com.matthers3.cryptomomo_test.BuildConfig;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class HttpRequest {

    public static JSONObject Request(String method, String targetUrl, List<URIParameter> params) {
        try {
            URIBuilder uriBuilder = new URIBuilder(targetUrl);
            uriBuilder.setParams(params);
            URL url = new URL(uriBuilder.getResult());

            HttpURLConnection API = (HttpURLConnection) url.openConnection();
            API.setRequestMethod(method);
            API.setRequestProperty("Accept", "application/json");
            API.setRequestProperty("X-CMC_PRO_API_KEY", BuildConfig.API_KEY);
            API.connect();

            int responseCode = API.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("[HTTP] Error: " + responseCode);
            }

            return ParseResponse(API);
        } catch (URISyntaxException | IOException e) {
            return null;
        }
    }

    private static JSONObject ParseResponse(HttpURLConnection connection) {
        try {
            StringBuilder response = new StringBuilder();
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line).append("\n");
            }
            bufferedReader.close();

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response.toString());

        } catch (IOException | ParseException e) {
            return null;
        }
    }

}
