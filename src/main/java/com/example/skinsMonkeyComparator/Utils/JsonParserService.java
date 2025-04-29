package com.example.skinsMonkeyComparator.Utils;

import com.example.skinsMonkeyComparator.ResponseStructure.PriceSteam;
import com.example.skinsMonkeyComparator.ResponseStructure.ItemResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class JsonParserService {

    public ItemResponse extractObjectsArray(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ItemResponse.class);
    }

    public PriceSteam parseSteamPrice(String json) throws JsonProcessingException {
        json = json.replace("zł", "").replaceAll("(?<=\\d),(?=\\d)", ".");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, PriceSteam.class);
    }
}