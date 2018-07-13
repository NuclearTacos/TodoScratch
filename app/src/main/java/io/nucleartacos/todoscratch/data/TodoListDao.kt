package io.nucleartacos.todoscratch.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TodoListDao {
    //Get All
    @Query("Select * From TodoList")
    fun getAll(): List<TodoList>

    //Get by Id
    @Query("Select * From TodoList Where id = :id")
    fun getById(id: Int): TodoList

    //Insert
    @Insert
    fun insert(todoList: TodoList): Long

    //Delete
    @Delete
    fun delete(todoList: TodoList)

    //Delete By Id
    @Query("Delete From TodoList Where id = :id")
    fun deleteById(id: Int)
}