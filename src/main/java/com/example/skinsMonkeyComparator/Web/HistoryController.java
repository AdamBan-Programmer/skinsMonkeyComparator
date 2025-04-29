package com.example.skinsMonkeyComparator.Web;

import com.example.skinsMonkeyComparator.Items.ItemRepository;
import com.example.skinsMonkeyComparator.Items.ItemBought;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping
    public String getHistoryPage(Model model)
    {
       List<ItemBought> itemsBought = itemRepository.findAllByOrderByIdDesc();
       model.addAttribute("itemsBought",itemsBought);
       return "history";
    }
}
