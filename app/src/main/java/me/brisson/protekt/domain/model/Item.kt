package me.brisson.protekt.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import me.brisson.protekt.R

interface Item {
    val id: String
    val name: String
    val createdAt: Long
    val type: Type

    enum class Type(
        @StringRes val stringResId: Int,
        @DrawableRes val drawableResId: Int,
        val enabled: Boolean
    ) {
        CREDENTIALS(R.string.credentials, R.drawable.ic_arcticons_locker, true),
        CREDIT_CARDS(R.string.credit_cards, R.drawable.ic_credit_card, false),
        IDENTITIES(R.string.identities, R.drawable.ic_id_card, false),
        SECRET_NOTES(R.string.secret_notes, R.drawable.ic_note, false)
    }
}