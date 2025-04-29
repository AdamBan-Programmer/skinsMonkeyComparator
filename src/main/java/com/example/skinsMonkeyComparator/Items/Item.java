package com.example.skinsMonkeyComparator.Items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String marketName;
    @SerializedName("price")//for OkHTTP3
    private double priceSM; // Skins Monkey price
    private double priceSteam; //Steam price
    private double priceDifference;
    @Transient
    private boolean worth;
    @Transient
    private boolean extinct;
    private String imageUrl;

    public Item(String marketName, double priceSM, double priceSteam, double priceDifference, boolean worth, boolean extinct, String imageUrl) {
        this.marketName = marketName;
        this.priceSM = priceSM;
        this.priceSteam = priceSteam;
        this.priceDifference = priceDifference;
        this.worth = worth;
        this.extinct = extinct;
        this.imageUrl = imageUrl;
    }

    public Item(String marketName) {
        this.marketName = marketName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(marketName, item.marketName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(marketName);
    }

    @Override
    public String toString()
    {
        return "name: " + this.marketName + " priceSM: " + this.priceSM + " priceSteam: " + this.priceSteam + " DIFFERENCE: " + this.priceDifference + " is Worth: " + this.isWorth();
    }
}











/*
package com.example.skinsMonkeyComparator.Items;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String marketName;
    @SerializedName("price")//for OkHTTP3
    private double priceSM; // Skins Monkey price
    @JsonAlias({"price", "lowest_price"})//for RestTemplate (Spring)
    private double priceSteam; //Steam price
    private double priceDifference;
    private boolean isWorth;
    private String imageUrl;

    public Item(String marketName, double priceSM, double priceSteam, double priceDifference, boolean isWorth, String imageUrl) {
        this.marketName = marketName;
        this.priceSM = priceSM;
        this.priceSteam = priceSteam;
        this.priceDifference = priceDifference;
        this.isWorth = isWorth;
        this.imageUrl = imageUrl;
    }

    public Item(String marketName) {
        this.marketName = marketName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(priceSM, item.priceSM) == 0 && Double.compare(priceSteam, item.priceSteam) == 0 && Double.compare(priceDifference, item.priceDifference) == 0 && isWorth == item.isWorth && Objects.equals(marketName, item.marketName) && Objects.equals(imageUrl, item.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketName, priceSM, priceSteam, priceDifference, isWorth, imageUrl);
    }

    @Override
    public String toString()
    {
        return "name: " + this.marketName + " priceSM: " + this.priceSM + " priceSteam: " + this.priceSteam + " DIFFERENCE: " + this.priceDifference + " is Worth: " + this.isWorth();
    }
}
* */