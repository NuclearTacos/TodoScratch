package io.nucleartacos.todoscratch

import android.app.Application
import android.arch.persistence.room.Room
import io.nucleartacos.todoscratch.data.TodoDatabase
import io.nucleartacos.todoscratch.data.TodoItem
import io.nucleartacos.todoscratch.data.TodoList


lateinit var db: TodoDatabase


class App : Application() {

    override fun onCreate() {
        db = Room.databaseBuilder(applicationContext, TodoDatabase::class.java, "TodoDatabase").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        var todoListsCount = db.TodoListDao().getNumberOfLists()

        if (todoListsCount == 0) {
            var tutorialTodoList = db.TodoListDao().insert(
                    TodoList(
                            null,
                            "TUTORIAL - Tap this list to view it.",
                            "This list will tell you how to use this app. Please tap this row learn more."
                    )
            ).toInt()
            db.TodoItemDao().insertList(mutableListOf(
                    TodoItem(
                            null,
                            "This item is done",
                            true,
                            todoListId = tutorialTodoList
                    ),
                    TodoItem(
                            null,
                            "This item is not done",
                            false,
                            todoListId = tutorialTodoList
                    ),
                    TodoItem(
                            null,
                            "Short tap a checkbox to mark it done",
                            false,
                            todoListId = tutorialTodoList
                    ),
                    TodoItem(
                            null,
                            "Hold a checkbox to delete the row.",
                            false,
                            todoListId = tutorialTodoList
                    ),
                    TodoItem(
                            null,
                            "Tap the button on the bottom of the main screen for a new list.",
                            false,
                            todoListId = tutorialTodoList
                    ),
                    TodoItem(
                            null,
                            "Enjoy!",
                            true,
                            todoListId = tutorialTodoList
                    )
            ))
        }

        super.onCreate()
    }
}
