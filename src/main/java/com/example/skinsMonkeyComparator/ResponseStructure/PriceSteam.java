package com.example.skinsMonkeyComparator.ResponseStructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceSteam {

    @JsonProperty("price")
    private double price;
    @JsonProperty("lowest_price")
    private double lowestPrice;
    @JsonProperty("median_price")
    private double medianPrice;

    public double getValue()
    {
        if(this.price > 0.0) //if 'price' is in Steam response
        {
            return this.price;
        }
        if(this.lowestPrice > 0.0)//if 'price' is not in Steam response but 'lowesPrice' is
        {
            return this.lowestPrice;
        }
        if(this.medianPrice > 0.0)//if 'price' and 'lowestPrice' are not in Steam response but 'medianPrice' is
        {
            return this.medianPrice;
        }
        return 0.0; //if none
    }
}
