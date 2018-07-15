package io.nucleartacos.todoscratch.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface TodoListDao {
    //Get All
    @Query("Select * From TodoList")
    fun getAll(): LiveData<List<TodoList>>

    //Get by Id
    @Query("Select * From TodoList Where id = :id")
    fun getById(id: Int): TodoList

    //Get number of lists
    @Query("Select Count(*) From TodoList")
    fun getNumberOfLists(): Int

    //Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoList: TodoList): Long

    //Delete
    @Delete
    fun delete(todoList: TodoList)

    //Delete By Id
    @Query("Delete From TodoList Where id = :id")
    fun deleteById(id: Int)
}