package io.nucleartacos.todoscratch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.nucleartacos.todoscratch.data.TodoList

class MainViewModel: ViewModel() {
    private var todoLists: LiveData<List<TodoList>>? = null
    fun getTodoLists(): LiveData<List<TodoList>> {
        if (todoLists == null) {
            todoLists = db.TodoListDao().getAll()
        }
        return todoLists!!
    }
}