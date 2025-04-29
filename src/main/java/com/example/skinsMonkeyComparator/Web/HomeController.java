package com.example.skinsMonkeyComparator.Web;

import com.example.skinsMonkeyComparator.Items.ItemRepository;
import com.example.skinsMonkeyComparator.Items.Item;
import com.example.skinsMonkeyComparator.Items.ItemBought;
import com.example.skinsMonkeyComparator.Items.ItemService;
import com.example.skinsMonkeyComparator.Search.Order;
import com.example.skinsMonkeyComparator.Search.SearchingParams;
import com.example.skinsMonkeyComparator.Search.SearchingStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/")
public class HomeController {

    ItemService itemService;
    ItemRepository itemRepository;
    Order order;

    public HomeController(ItemService itemService, ItemRepository itemRepository, Order order) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.order = order;
    }

    @GetMapping
    public String getPage(Model model) {
        if(!model.containsAttribute("searchingParams") && !model.containsAttribute("itemsList"))
        {
            model.addAttribute("searchingParams",new SearchingParams());
            model.addAttribute("itemsList",new ArrayList<Item>());
        }
        return "home";
    }

    @PostMapping
    public String compare(@ModelAttribute SearchingParams params, Model model)
    {
        order.getInstance().itemsSet = new HashSet<Item>();
        order.getInstance().searchingStatus = new SearchingStatus();
        ArrayList<Item> itemsList = itemService.compareItemsPrice(params);
        model.addAttribute("itemsList",itemsList);
        return "home";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute SearchingParams params, RedirectAttributes redirectAttributes)
    {
        ArrayList<Item> itemsList = itemService.compareItemsPrice(params);
        redirectAttributes.addFlashAttribute("itemsList",itemsList);
        redirectAttributes.addFlashAttribute("searchingParams", params);
        return "redirect:/";
    }

    @PostMapping("/cancel")
    public String cancel(@ModelAttribute SearchingParams params,RedirectAttributes redirectAttributes)
    {
        order.getInstance().searchingStatus.isWorking=false;
        redirectAttributes.addFlashAttribute("searchingParams",params);
        return "redirect:/";
    }

    @PostMapping("/showHistory")
    public String showHistory()
    {
        return "redirect:/history";
    }

    @PostMapping("/buy")
    public String BuyItem(@ModelAttribute ItemBought itemBought, RedirectAttributes redirectAttributes)
    {
        try {
            Item item = itemService.getSelectedItemByName(itemBought.getMarketName());
            BeanUtils.copyProperties(item,itemBought);
            itemRepository.save(itemBought);
        }
        catch (NoSuchElementException n)
        {
            System.out.println("Cannot save. Item not found!");
        }
        ArrayList<Item> itemsList = new ArrayList<>(order.getInstance().itemsSet);
        itemService.sortItemsList(itemsList);
        redirectAttributes.addFlashAttribute("itemsList",itemsList);
        redirectAttributes.addFlashAttribute("searchingParams", order.getInstance().searchingParams);
        return "redirect:/";
    }

    @ModelAttribute("buyItem")
    public ItemBought getBoughtItem() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().format(formatter);
        return new ItemBought(1,date);
    }
}
