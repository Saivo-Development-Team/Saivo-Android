package com.saivo.recommendo.view.fragment.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.saivo.recommendo.R
import com.saivo.recommendo.util.helpers.toastMessage
import com.saivo.recommendo.view.main.page.DataSource
import com.saivo.recommendo.view.main.page.RecyclerItem
import com.saivo.recommendo.view.main.page.RecyclerViewAdapter
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

    fun test(test: String) {
        DataSource.setData(test)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var input = ""
        initRecyclerView()
        addDataSet()

        preference_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                input = p0.toString()
            }

        })
        //code for when Add Button is clicked
        add_button.setOnClickListener {
            toastMessage(this@PreferencesFragment.context, "Your $input Preference has been added")
            test(input)


        }


        //code for when Remove Button is clicked
        remove_button.setOnClickListener {
            toastMessage(this@PreferencesFragment.context, "Your $input Preference has been removed")
            DataSource.getData().apply { forEach { if (it.preference.equals(input)) remove(it) } }
        }


    }

    private fun addDataSet() {
        val data = DataSource.getData()
        recyclerAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_preferences.apply {
            recycler_preferences.layoutManager =
                LinearLayoutManager(context)  // May need to be @preferencesfragment
            recyclerAdapter = RecyclerViewAdapter()
            adapter = recyclerAdapter
        }

    }

}




