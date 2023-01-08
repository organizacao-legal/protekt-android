package me.brisson.protekt.domain.model

import java.util.UUID

data class Credential(
    val id: String = UUID.randomUUID().toString(),
    val url: String = "",
    val name: String = "",
    val username: String = "",
    val password: String = "@Aa12345",
    val image: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Item {

    // Todo: passar essa função pro back e retornar o valor float no objeto
    fun calculatePasswordSafety(): Float {
        val lower = password.contains(".*[a-z].*".toRegex())
        val upper = password.contains(".*[A-Z].*".toRegex())
        val numeric = password.contains(".*\\d.*".toRegex())
        val specialChar = password.contains(".*[-+_!@#\$%^&*., ?].*".toRegex())
        val hasAtLeast8Char = password.length >= 8

        listOf(lower, upper, numeric, specialChar, hasAtLeast8Char).let { conditionList ->
            val singleItemPercentage = 1f/conditionList.size
            val filteredListSize = conditionList.filter { it }.size
            return  filteredListSize * singleItemPercentage
        }
    }
}

interface Item

val mockedItemList = listOf<Item>(
    Credential(
        image = "https://logo.clearbit.com/https://twitter.com",
        name = "Twitter",
        username = "@JonDoe",
        url = "http://twitter.com"
    ),
    Credential(
        name = "Twitter",
        username = "@JonDoe",
        url = "http://twitter.com"
    )
)