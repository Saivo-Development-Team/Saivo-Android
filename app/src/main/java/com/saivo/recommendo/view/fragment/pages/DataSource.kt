package com.saivo.recommendo.view.main.page

object DataSource {
    private val list = ArrayList<RecyclerItem>()
    fun setData(preference: String) {
        list.add(
            RecyclerItem(
                preference
            )
        )
    }

    fun getData(): ArrayList<RecyclerItem> {
        return list
    }

}


