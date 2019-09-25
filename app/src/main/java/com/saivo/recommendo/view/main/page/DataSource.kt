package com.saivo.recommendo.view.main.page

import androidx.recyclerview.widget.RecyclerView

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<RecyclerItem>{
            val list = ArrayList<RecyclerItem>()
            list.add( RecyclerItem(
                "Preference"
            )
            )

            list.add( RecyclerItem(
                "Preference2"
            )
            )
            list.add( RecyclerItem(
                "Preference3"
            )
            )





            return list
        }
    }
}


