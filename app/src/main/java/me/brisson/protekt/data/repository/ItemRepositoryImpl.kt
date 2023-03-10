package me.brisson.protekt.data.repository

import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.domain.model.Item
import me.brisson.protekt.domain.model.Password
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

    override suspend fun postItem(item: Item): Result<Item> {
        return try {
            val existingItem = mockedItemList.find { it.id == item.id }
            if (existingItem != null) {
                mockedItemList.set( index = mockedItemList.indexOf(existingItem), item)
            } else {
                mockedItemList.add(item)
            }

            Result.Success(item)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

var mockedItemList = mutableListOf<Item>(
    Credential(
        image = "https://logo.clearbit.com/https://twitter.com",
        name = "Twitter",
        username = "@JonDoe",
        password = Password("@Aa12345"),
        url = "http://twitter.com"
    ),
    Credential(
        name = "Twitter",
        username = "@JonDoe",
        password = Password("123"),
        url = "http://twitter.com"
    )
)