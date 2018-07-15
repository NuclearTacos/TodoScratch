package io.nucleartacos.todoscratch.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.nucleartacos.todoscratch.data.TodoList
import io.nucleartacos.todoscratch.db

class MainViewModel: ViewModel() {
    private var todoLists: LiveData<List<TodoList>>? = null
    fun getTodoLists(): LiveData<List<TodoList>> {
        if (todoLists == null) {
            todoLists = db.TodoListDao().getAll()
        }
        return todoLists!!
    }
}