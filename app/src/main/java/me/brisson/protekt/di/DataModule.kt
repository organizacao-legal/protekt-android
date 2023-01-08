package me.brisson.protekt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.brisson.protekt.data.repository.ItemRepositoryImpl
import me.brisson.protekt.domain.repository.ItemRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideItemRepository() : ItemRepository = ItemRepositoryImpl()
}
