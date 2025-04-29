package com.example.skinsMonkeyComparator.Items;

import com.example.skinsMonkeyComparator.ResponseStructure.Asset;
import com.example.skinsMonkeyComparator.API.ResponseService;
import com.example.skinsMonkeyComparator.Search.Order;
import com.example.skinsMonkeyComparator.Search.SearchingParams;
import com.example.skinsMonkeyComparator.Utils.FormatConverter;
import com.example.skinsMonkeyComparator.Utils.JsonParserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.*;

@Service
public class ItemService {

    ResponseService responseService;
    FormatConverter formatConverter;
    Order order;

    public ItemService(ResponseService responseService, FormatConverter formatConverter,Order order) {
        this.responseService = responseService;
        this.formatConverter = formatConverter;
        this.order = order;
    }

    public ArrayList<Item> compareItemsPrice(SearchingParams newParams)
    {
        order.getInstance().searchingStatus.isWorking = true;
        order.getInstance().searchingParams = newParams;
        searchNewItems(); //from SkinsMonkey
        ArrayList<Item> itemsList = setItemsSteamPrice(order.getInstance());
        sortItemsList(itemsList);
        this.order.getInstance().getSearchingStatus().isWorking = false;
        return itemsList;
    }

    //sorting DESC by priceDifference
    public void sortItemsList(List<Item> items)
    {
        boolean shouldRepeat = true;
        while(shouldRepeat)
        {
            shouldRepeat = false;
            int size = items.size();
            for(int i =0;i<size;i++)
            {
                if(i < size-1) {
                    double current = items.get(i).getPriceDifference();
                    double next = items.get(i + 1).getPriceDifference();
                    if (current < next) {
                        Collections.swap(items,i,i+1);
                        shouldRepeat = true;
                    }
                }
            }
        }
    }

    //gets available items from SkinsMonkey and adds them into itemsSet
    private void searchNewItems()
    {
        setAllItemsExtinct(order.getInstance().itemsSet);
        scanItemsFromSkinsMonkey(order.getInstance());
    }

    //default value
    private void setAllItemsExtinct(HashSet<Item> itemsSet) //getItemsToCheck will set non-extinct when it will be available in SkinsMonkey
    {
        for(Item item : itemsSet)
        {
            item.setExtinct(true);
        }
    }

    //searching new items from SkinsMonkey_API
    public void scanItemsFromSkinsMonkey(Order order)
    {
        SearchingParams params = order.searchingParams;
        try {
            params.setLimit(300);
            params.setOffset(0);
            List<Asset> assets;
            do {
                assets = responseService.getSkinsMonkeyResponse(params);
                params.setOffset(params.getOffset() + params.getLimit());
                for (Asset asset : assets) {
                    Item item = changeAssetToItem(asset,params);
                    if(order.itemsSet.contains(item))
                    {
                        setExistingItemExtinct(item);
                    }
                    order.itemsSet.add(item);
                }
                Thread.sleep(100);//requests timeout for SkinsMonkey (avoid ban)
            }
            while(assets.size() > 0);
            {
                order.searchingStatus.itemsToCompare = order.itemsSet.size();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //specific API structure
    private Item changeAssetToItem(Asset asset, SearchingParams params)
    {
        Item item = asset.getItem();
        item.setPriceSM(formatConverter.roundValue((item.getPriceSM() / 100) * params.getCurrencyValue()));
        String steamImageUrl = item.getImageUrl().
                 replace("https://cdn.skinsmonkey.com/economy/image/","")
                .replace("https://steamcommunity-a.akamaihd.net/economy/image/","");
        item.setImageUrl(steamImageUrl);
        item.setExtinct(false);
        return item;
    }

    private void setExistingItemExtinct(Item existingItem)
    {
        for(Item scannedItem : order.getInstance().itemsSet)
        {
            if(scannedItem.equals(existingItem)) {
                scannedItem.setExtinct(false);
            }
        }
    }

    public ArrayList<Item> setItemsSteamPrice(Order order)
    {
        ArrayList<Item> itemsList = new ArrayList<>(order.itemsSet);
        order.searchingStatus.itemsToCompare = itemsList.size();
        final float STEAM_TAX = 1.15f;
        for(int i =1;i<=itemsList.size();i++)
        {
            if(order.searchingStatus.isWorking) {
                Item item = itemsList.get((i - 1));
                if (item.getPriceSteam() == 0.0) {
                    String itemName = item.getMarketName();
                    try {
                        item.setPriceSteam(getSteamPrice(itemName));
                        item.setPriceDifference(formatConverter.roundValue((item.getPriceSteam() / STEAM_TAX) - item.getPriceSM()));
                        item.setWorth(item.getPriceSteam() > 0 && item.getPriceDifference() >= 0);
                    } catch (HttpClientErrorException.TooManyRequests | HttpServerErrorException e) {
                        System.out.println("Steam: Too Many Requests!");
                        return itemsList;//Steam: Too Many Requests or bad gateway (502)
                    } catch (JsonProcessingException j) {
                        i -= 1;
                    } finally {
                        try {
                            System.out.println("updated: " + i + "/" + itemsList.size() + " | " + itemName + " | SkinksMonkey: " + item.getPriceSM() + "zł. | Steam: " + item.getPriceSteam() + "zł. | ");
                            float delay = order.searchingParams.getRequestsDelay();
                            order.searchingStatus.itemsCompared+=1;
                            order.searchingStatus.progress = (int) (((double) (order.searchingStatus.itemsCompared) / itemsList.size()) * 100);
                            Thread.sleep((long) (delay * 1000));//changing to ms
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            else {
                return itemsList;
            }
        }
        return itemsList;
    }

    //returns double value
    public double getSteamPrice(String itemName) throws JsonProcessingException{
        JsonParserService parser = new JsonParserService();
        String json = responseService.getSteamResponse(itemName);
        return parser.parseSteamPrice(json).getValue();
    }

    //returns selected item by user intended to save in database (bought)
    public Item getSelectedItemByName(String name)
    {
        for (Item item : order.getInstance().itemsSet) {
            if (item.getMarketName().equals(name)) {
                return item;
            }
        }
        throw new NoSuchElementException();
    }
}
