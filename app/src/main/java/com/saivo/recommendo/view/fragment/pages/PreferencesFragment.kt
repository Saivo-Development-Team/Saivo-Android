package com.saivo.recommendo.view.fragment.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.saivo.recommendo.R
import com.saivo.recommendo.view.fragment.dialog.IPreferenceDialogListener
import com.saivo.recommendo.view.fragment.dialog.PreferenceDialogFragment
import com.saivo.recommendo.view.objects.RecyclerAdapter
import com.saivo.recommendo.view.objects.preferences.PreCard
import kotlinx.android.synthetic.main.fragment_preferences.*

class PreferencesFragment : Fragment(), IPreferenceDialogListener {
    private val preferenceDialogFragment = PreferenceDialogFragment()
    private val recyclerAdapter = RecyclerAdapter(PreCard::class.java)
    private val data = arrayListOf<PreCard>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preferences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@PreferencesFragment.context)
            adapter = recyclerAdapter
        }
        setRecyclerItems()
        floating_action_button.setOnClickListener {
            preferenceDialogFragment.setPreferenceDialogListener(this)
            preferenceDialogFragment.show(
                this.parentFragmentManager,
                "PreferenceBottomSheet"
            )
        }
    }

    private fun setRecyclerItems() {
        data.add(
            PreCard(
                likes = "Coding",
                dislikes = "Driving",
                category = "Activity",
                description = "In my Free Time I code, but I hate it when I have to Drive"
            )
        )
        data.add(
            PreCard(
                likes = "Pizza",
                dislikes = "Green Beans",
                category = "Food",
                description = "So am a Foodie, and Love to it all kings of things, just not Green Beans"
            )
        )
        data.add(
            PreCard(
                likes = "12345678901234567",
                dislikes = "12345678901234567",
                category = "Numbers",
                description = "So am a Number guys, and Love to tyr new things with numbers, yet I still fails maths LOL"
            )
        )
        recyclerAdapter.addToListItems(data)
    }

    private fun addCardData(card: PreCard): ArrayList<PreCard> {
        return data.apply { add(card) }
    }

    override fun onPreferenceSaved(card: PreCard) {
        recyclerAdapter.addToListItems(addCardData(card))
        recyclerAdapter.notifyDataSetChanged()
    }

}




