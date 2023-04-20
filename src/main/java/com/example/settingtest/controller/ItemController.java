package com.example.settingtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String itemForm(){
        return "/item/itemForm";
    }

    @GetMapping(value = "/admin/items")
    public String itemManage(){
        return "/item/itemManage";
    }

    @GetMapping(value = "/cart")
    public String itemCart(){
        return "/item/itemCart";
    }

    @GetMapping(value = "/orders")
    public String itemHistory(){
        return "/item/itemHistory";
    }
}