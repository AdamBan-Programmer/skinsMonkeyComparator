package com.example.skinsMonkeyComparator.Utils;

import org.springframework.stereotype.Service;

@Service
public class FormatConverter {

    //rounds value: x,xx
    public double roundValue(double value)
    {
        return (double) Math.round((value * 100)) / 100;
    }
}
