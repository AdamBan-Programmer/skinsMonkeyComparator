package com.example.skinsMonkeyComparator.API;


import com.example.skinsMonkeyComparator.Search.SearchingParams;
import okhttp3.Request;
import org.springframework.stereotype.Service;

@Service
public class WebHeaders {

    public Request getSkinsMonkeyHeaders(SearchingParams params)// headers necessary to send request
    {
        int priceMin = (int) (params.getPriceMin()/(params.getCurrencyValue()/100));
        int priceMax = (int) (params.getPriceMax()/(params.getCurrencyValue()/100));

        Request headers = new Request.Builder()
                .url("https://skinsmonkey.com/api/inventory?limit="+ params.getLimit() +"&offset="+ params.getOffset() +"&appId=730&sort=price-desc&priceMin="+ priceMin +"&priceMax=" + priceMax)
                .method("GET", null)
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("accept-language", "pl-PL,pl;q=0.9")
                .addHeader("priority", "u=1, i")
                .addHeader("referer", "https://skinsmonkey.com/pl/trade")
                .addHeader("sec-ch-ua", "\"Opera GX\";v=\"116\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                .addHeader("x-csrf-token", "9ed2e23e22466bc22b1c35296377c5cfd7195bf197196467433283adef151e23")
                .addHeader("Cookie", "sm_id=oah9H0pKJ74vG1xXpbvoLOaGl1YNnX0F; sm_udg=730; sm_uig=730; sm_sig=730; crisp-client%2Fsession%2Fc65b3c05-d915-4ebe-a091-3a9d4d62bcb5=session_dba26d39-a7b3-4b96-bf7c-208183c3665f; sm_sid=iZ0NgRGxxot29eBaQ7J3BVwJCqeH8rhM; sm_locale=pl; sm_hfdt=true; sm_heb=")
                .build();
        return headers;
    }
}
