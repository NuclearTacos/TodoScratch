package io.nucleartacos.todoscratch.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

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
    fun getByListId(id: Int): LiveData<List<TodoItem>>

    //Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoItem: TodoItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(todoItems: List<TodoItem>)

    //Delete
    @Delete
    fun delete(todoItem: TodoItem)

    //Delete By Id
    @Query("Delete From TodoItem Where id = :id")
    fun deleteById(id: Int)

    //Delete By List Id
    @Query("Delete From TodoItem Where todoListId = :id")
    fun deleteByListId(id: Int)
}