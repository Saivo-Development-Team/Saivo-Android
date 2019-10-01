package com.saivo.recommendo.view.fragment.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.saivo.recommendo.R

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    PreferenceDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [IPreferenceDialogListener].
 */
class PreferenceDialogFragment : BottomSheetDialogFragment() {
    private var preferenceDialogListener: IPreferenceDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preference_dialog, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        runCatching {
            preferenceDialogListener = context as IPreferenceDialogListener
        }
    }

    override fun onDetach() {
        preferenceDialogListener = null
        super.onDetach()
    }

}
