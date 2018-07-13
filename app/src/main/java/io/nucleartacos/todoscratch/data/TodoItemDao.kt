package io.nucleartacos.todoscratch.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TodoItemDao {
    //Get All
    @Query("Select * From TodoItem")
    fun getAll(): List<TodoItem>

    //Get by Id
    @Query("Select * From TodoItem Where id = :id")
    fun getById(id: Int): TodoItem

    //Get by List Id
    @Query("Select * From TodoItem Where todoListId = :id")
    fun getByListId(id: Int): List<TodoItem>

    //Insert
    @Insert
    fun insert(todoItem: TodoItem): Long

    //Delete
    @Delete
    fun delete(todoItem: TodoItem)

    //Delete By Id
    @Query("Delete From TodoItem Where id = :id")
    fun deleteById(id: Int)
}