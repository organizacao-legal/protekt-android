package me.brisson.protekt.domain.model

import java.util.UUID

data class Credential(
    override val id: String = UUID.randomUUID().toString(),
    var url: String = "",
    override var name: String = "",
    var username: String = "",
    var password: Password = Password(""),
    val image: String = "",
    var note: String = "",
    override val createdAt: Long = System.currentTimeMillis(),
    override val type: Item.Type = Item.Type.CREDENTIALS
) : Item
