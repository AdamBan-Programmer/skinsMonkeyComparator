package com.example.skinsMonkeyComparator.Search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class SearchingParams {

    private float priceMin;
    private float priceMax;
    private float currencyValue;
    private float requestsDelay;
    private int limit;
    private int offset;
}
