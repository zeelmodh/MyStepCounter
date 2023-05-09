package com.example.syncronizedscroll

data class Category(var category: String, var ItemList: Item)

data class Item(var item: List<String>, var collapseItem: CollapseItem)

data class CollapseItem(var collapseItem: List<String>)