package io.nucleartacos.todoscratch.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class TodoList (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val description: String
)