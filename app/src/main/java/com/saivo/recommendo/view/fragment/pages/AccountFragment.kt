package com.saivo.recommendo.view.fragment.pages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.saivo.recommendo.R
import com.saivo.recommendo.util.helpers.toastMessage
import com.saivo.recommendo.view.viewmodel.user.AccountViewModel
import kotlinx.android.synthetic.main.account_fragment.*

class AccountFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        account_profile_image.setOnClickListener {
            toastMessage(this.requireContext(), "Clicked")
        }
    }
}
