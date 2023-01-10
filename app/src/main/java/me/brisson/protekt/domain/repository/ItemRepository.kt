package me.brisson.protekt.domain.repository

import me.brisson.protekt.domain.model.Item
import me.brisson.protekt.domain.model.Result

interface ItemRepository {

    suspend fun getAllItems() : Result<List<Item>>
}