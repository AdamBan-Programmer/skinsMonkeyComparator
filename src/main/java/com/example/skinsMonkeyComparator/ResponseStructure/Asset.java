package com.example.skinsMonkeyComparator.ResponseStructure;

import com.example.skinsMonkeyComparator.Items.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Asset {

    private Item item;

    public Asset(Item item) {
        this.item = item;
    }
}
