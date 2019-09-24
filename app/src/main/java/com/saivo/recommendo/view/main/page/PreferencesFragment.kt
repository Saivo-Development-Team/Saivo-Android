package com.saivo.recommendo.view.main.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.saivo.recommendo.R
import kotlinx.android.synthetic.main.fragment_preferences.*
import kotlinx.android.synthetic.main.fragment_preferences.view.*


class PreferencesFragment : Fragment() {

    private lateinit var recyclerAdapter: RecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preferences, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addDataSet()


        //code for when Add Button is clicked
        add_button.setOnClickListener{
        var input = preference_input

        }

        //code for when Remove Button is clicked
        remove_button.setOnClickListener{

        }




    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        recyclerAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_preferences.apply {
            recycler_preferences.layoutManager = LinearLayoutManager(context)  // May need to be @preferencesfragment
            recyclerAdapter = RecyclerViewAdapter()
            adapter = recyclerAdapter
        }

    }

    }




