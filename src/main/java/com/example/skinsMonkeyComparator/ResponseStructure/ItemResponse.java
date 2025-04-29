package com.example.skinsMonkeyComparator.ResponseStructure;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemResponse {

    private List<Asset> assets;

    public ItemResponse(List<Asset> assets) {
        this.assets = assets;
    }
}
