package com.saivo.recommendo.view.main.page

import androidx.recyclerview.widget.RecyclerView

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<RecyclerView>{
            val list = ArrayList<RecyclerView>()
            list.add( RecyclerView(
                "Preference"
            )
            )

            list.add( RecyclerView(
                "Preference2"
            )
            )
            list.add( RecyclerView(
                "Preference3"
            )
            )





            return list
        }
    }
}


