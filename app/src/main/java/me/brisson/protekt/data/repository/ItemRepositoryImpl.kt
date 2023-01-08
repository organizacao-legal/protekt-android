package me.brisson.protekt.data.repository

import me.brisson.protekt.domain.model.Item
import me.brisson.protekt.domain.model.Result
import me.brisson.protekt.domain.model.mockedItemList
import me.brisson.protekt.domain.repository.ItemRepository

class ItemRepositoryImpl : ItemRepository {
    override suspend fun getAllItems(): Result<List<Item>> {
        return Result.Success(mockedItemList)
    }
}