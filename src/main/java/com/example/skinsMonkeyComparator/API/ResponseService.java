package com.example.skinsMonkeyComparator.API;

import com.example.skinsMonkeyComparator.ResponseStructure.Asset;
import com.example.skinsMonkeyComparator.ResponseStructure.ItemResponse;
import com.example.skinsMonkeyComparator.Search.SearchingParams;
import com.example.skinsMonkeyComparator.Utils.JsonParserService;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;


@Service
public class ResponseService {

    JsonParserService parser;
    WebHeaders webHeaders;

    public ResponseService(JsonParserService parser, WebHeaders webHeaders) {
        this.parser = parser;
        this.webHeaders = webHeaders;
    }

    //SkinsMonkey API response (using OkHTTP)
    public List<Asset> getSkinsMonkeyResponse(SearchingParams params){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = webHeaders.getSkinsMonkeyHeaders(params);
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                String json = response.body().string();
                return parser.extractObjectsArray(json).getAssets();
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Steam API response (RestTemplate)
    public String getSteamResponse(String itemName)
    {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://steamcommunity.com/market/priceoverview/?currency=6&appid=730&market_hash_name=" + itemName, String.class);
    }
}