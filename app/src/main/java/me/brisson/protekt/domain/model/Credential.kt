package me.brisson.protekt.domain.model

import java.util.UUID

data class Credential(
    override val id: String = UUID.randomUUID().toString(),
    val url: String = "",
    override val name: String = "",
    val username: String = "",
    val password: Password = Password(""),
    val image: String = "",
    val note: String = "",
    override val createdAt: Long = System.currentTimeMillis(),
    override val type: Item.Type = Item.Type.CREDENTIALS
) : Item
