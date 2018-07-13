package io.nucleartacos.todoscratch.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity
data class TodoItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val isDone: Boolean,
    @ForeignKey(entity = TodoList::class, parentColumns = ["id"], childColumns = ["todoListId"])
    val todoListId: Int
)