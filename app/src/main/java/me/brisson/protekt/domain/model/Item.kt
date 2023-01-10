package me.brisson.protekt.domain.model

import androidx.annotation.StringRes
import me.brisson.protekt.R

interface Item {
    val id: String
    val createdAt: Long
    val type: Type

    enum class Type(@StringRes val stringResId: Int) {
        CREDENTIALS(R.string.credentials),
        CREDIT_CARDS(R.string.credit_cards),
        IDENTITIES(R.string.identities),
        SECRET_NOTES(R.string.secret_notes)
    }
}