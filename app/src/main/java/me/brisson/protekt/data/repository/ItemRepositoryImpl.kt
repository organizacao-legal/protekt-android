package me.brisson.protekt.data.repository

import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.domain.model.Item
import me.brisson.protekt.domain.model.Result
import me.brisson.protekt.domain.repository.ItemRepository

class ItemRepositoryImpl : ItemRepository {
    override suspend fun getItemById(itemId: String): Result<Item> {
        val item = mockedItemList.find { it.id == itemId }

        return if (item == null) {
            Result.Error(NullPointerException())
        } else {
            Result.Success(item)
        }
    }

    override suspend fun getAllItems(): Result<List<Item>> {
        return Result.Success(mockedItemList)
    }
}

val mockedItemList = listOf<Item>(
    Credential(
        image = "https://logo.clearbit.com/https://twitter.com",
        name = "Twitter",
        username = "@JonDoe",
        password = "@Aa12345",
        url = "http://twitter.com"
    ),
    Credential(
        name = "Twitter",
        username = "@JonDoe",
        password = "123",
        url = "http://twitter.com"
    )
)