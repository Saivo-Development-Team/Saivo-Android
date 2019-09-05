package com.saivo.recommendo.util.helpers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

private val emailRegex = Regex("([^\\s.<]+\\S+[\\w\\d\"_])@([-\\w\\[.]+[\\w\\]]+)")
private val passwordRegex = Regex("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%&]).{8,16})")

fun isNotEmpty(text: String): Boolean {
    return text.isNotEmpty()
}

fun isEmail(text: String): Boolean {
    return text.matches(emailRegex)
}

fun isPassword(text: String): Boolean {
    return text.matches(passwordRegex)
}

fun formNotEmpty(form: Array<EditText>): Boolean {
    for (input in form) if (input.text!!.isEmpty()) return false
    return true
}

fun isValidInput(text: String, vararg checker: (text: String) -> Boolean): Boolean {
    for (c in checker) if (!c(text)) return false
    return true
}

fun textWatcher(
    after: ((edit: Editable?) -> Unit)? = null,
    before: ((char: CharSequence?) -> Unit)? = null,
    onText: ((char: CharSequence?) -> Unit)? = null
): TextWatcher {
    return object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            if (after != null) after(p0)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (before != null) before(p0)
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (onText != null) onText(p0)
        }
    }
}
