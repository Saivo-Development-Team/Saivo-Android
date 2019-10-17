package com.saivo.recommendo.view.fragment.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saivo.recommendo.R
import com.saivo.recommendo.data.model.domain.Preference
import com.saivo.recommendo.view.fragment.dialog.IPreferenceDialogListener
import com.saivo.recommendo.view.fragment.dialog.PreferenceDialogFragment
import com.saivo.recommendo.view.objects.RecyclerAdapter
import com.saivo.recommendo.view.objects.preferences.PreCard
import com.saivo.recommendo.view.viewmodel.ViewModelFactory
import com.saivo.recommendo.view.viewmodel.preference.IPreferenceViewModel
import com.saivo.recommendo.view.viewmodel.preference.PreferenceViewModel
import kotlinx.android.synthetic.main.fragment_preferences.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PreferencesFragment : Fragment(), KodeinAware, IPreferenceDialogListener {
    override val kodein: Kodein by closestKodein()

    private lateinit var preferenceViewModel: IPreferenceViewModel
    private val viewModelFactory: ViewModelFactory by instance()
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
        preferenceViewModel =
            ViewModelProvider(this, viewModelFactory).get(PreferenceViewModel::class.java)
        loadPreferences()
        preference_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@PreferencesFragment.context)
            adapter = recyclerAdapter
        }

        recyclerAdapter.addToListItems(data)
        floating_action_button.setOnClickListener {
            preferenceDialogFragment.setPreferenceDialogListener(this)
            preferenceDialogFragment.show(
                this.parentFragmentManager,
                "PreferenceBottomSheet"
            )
        }
    }

    private fun loadPreferences() = CoroutineScope(IO).launch {
        preferenceViewModel.getPreference().forEach {
            data.add(
                PreCard(
                    likes = it.likes,
                    dislikes = it.dislikes,
                    category = it.category,
                    description = it.description
                )
            )
        }
    }

    private fun addCardData(card: PreCard): ArrayList<PreCard> {
        return data.apply { add(card) }
    }

    override fun onPreferenceSaved(card: PreCard) {
        CoroutineScope(IO).launch {
            addPreferenceFromView(card)
        }
        recyclerAdapter.addToListItems(addCardData(card))
        recyclerAdapter.notifyDataSetChanged()
    }

    private suspend fun addPreferenceFromView(card: PreCard) {
        preferenceViewModel.addPreference(
            Preference(
                id = "",
                likes = card.likes,
                dislikes = card.dislikes,
                category = card.category,
                description = card.description
            )
        )
    }

}




