package com.ankit.todotask.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ankit.todotask.models.Product

@Dao
interface RoomInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<Product>)

    @Query("select * from products where isComplete=:isCompleted")
    fun getList(isCompleted: Boolean): LiveData<List<Product>>

    @Update
    fun updateTask(item:Product)

    @Query("SELECT (SELECT COUNT(*) FROM products) == 0")
    fun isEmpty(): Boolean

    @Delete
    fun deleteTask(item: Product)
}