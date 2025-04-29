package com.example.skinsMonkeyComparator.API;

import com.example.skinsMonkeyComparator.Search.Order;
import com.example.skinsMonkeyComparator.Search.SearchingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class SearchingStatusController {

    @Autowired
    Order order;

    @GetMapping("/status")
    public SearchingStatus getStatus() //used to send searchingStatus to website
    {
        return order.getInstance().searchingStatus;
    }
}
