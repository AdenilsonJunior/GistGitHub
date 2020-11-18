package br.com.adenilson.database.di

import android.content.Context
import androidx.room.Room
import br.com.adenilson.core.di.ApplicationContext
import br.com.adenilson.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDatabaseModule {
    companion object {
        @Provides
        @Singleton
        fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).build()
        }
    }
}