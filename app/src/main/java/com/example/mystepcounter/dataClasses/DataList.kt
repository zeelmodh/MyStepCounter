package com.example.mystepcounter.dataClasses

data class DataList(var titleList: ArrayList<ListTitle>, var desList: Description) {
    class ListTitle(var title: String)
    //    class ListDescription(var description: String)
    class Description(var descriptionList: ArrayList<ListDescription>)
    class ListDescription(var description: String, var title: String, var index: Int)
}
