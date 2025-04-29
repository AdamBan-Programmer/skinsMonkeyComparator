package com.example.skinsMonkeyComparator.Search;

import com.example.skinsMonkeyComparator.Items.Item;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Data
@Component
public final class Order {  //Singleton

   public HashSet<Item> itemsSet;
   public SearchingParams searchingParams;
   public SearchingStatus searchingStatus;

   private static Order instance = null;

    public Order(HashSet<Item> itemsSet, SearchingParams searchingParams, SearchingStatus searchingStatus) {
        this.itemsSet = itemsSet;
        this.searchingParams = searchingParams;
        this.searchingStatus = searchingStatus;
    }

    public Order getInstance()
    {
        if(instance == null)
        {
            instance = new Order(new HashSet<Item>(),new SearchingParams(),new SearchingStatus(false,0,0,0));
        }
        return instance;
    }
}
