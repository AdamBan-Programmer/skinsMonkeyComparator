package com.example.skinsMonkeyComparator.Search;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class SearchingStatus {

    public boolean isWorking;
    public int itemsToCompare;
    public int itemsCompared;
    public int progress;

    public SearchingStatus(boolean isWorking, int itemsToCompare, int itemsCompared, int progress) {
        this.isWorking = isWorking;
        this.itemsToCompare = itemsToCompare;
        this.itemsCompared = itemsCompared;
        this.progress = progress;
    }
}
