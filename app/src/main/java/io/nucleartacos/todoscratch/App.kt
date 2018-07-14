package io.nucleartacos.todoscratch

import android.app.Application
import android.arch.persistence.room.Room
import android.widget.Toast
import io.nucleartacos.todoscratch.data.TodoDatabase
import kotlin.concurrent.thread


lateinit var db: TodoDatabase


class App : Application() {

    override fun onCreate() {
        db = Room.databaseBuilder(applicationContext, TodoDatabase::class.java, "TodoDatabase").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        super.onCreate()
    }
}
