package io.nucleartacos.todoscratch.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = [TodoItem::class, TodoList::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun TodoItemDao(): TodoItemDao
    abstract fun TodoListDao(): TodoListDao
}