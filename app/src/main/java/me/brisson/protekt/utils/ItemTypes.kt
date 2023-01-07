package me.brisson.protekt.utils

import androidx.annotation.StringRes
import me.brisson.protekt.R

enum class ItemTypes(@StringRes val stringResId: Int) {
    CREDENTIALS(R.string.credentials),
    CREDIT_CARDS(R.string.credit_cards),
    IDENTITIES(R.string.identities),
    SECRET_NOTES(R.string.secret_notes)
}