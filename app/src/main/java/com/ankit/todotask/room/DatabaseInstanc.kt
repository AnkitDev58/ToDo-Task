package com.ankit.todotask.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ankit.todotask.models.ImagesListConverter
import com.ankit.todotask.models.Product


@Database(entities = [Product::class], version = 1)
@TypeConverters(ImagesListConverter::class)
abstract class DatabaseInstance : RoomDatabase() {

    abstract fun dao(): RoomInterface

    companion object {
        private var instance: DatabaseInstance? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseInstance {
            if (instance == null)
                instance = createRoomInstance(ctx)

            return instance ?: createRoomInstance(ctx)

        }

        private fun createRoomInstance(ctx: Context) = Room.databaseBuilder(
            ctx.applicationContext, DatabaseInstance::class.java,
            "database_product"
        ).build()

    }


}