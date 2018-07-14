package io.nucleartacos.todoscratch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.nucleartacos.todoscratch.adapters.TodoListListAdapter
import io.nucleartacos.todoscratch.data.TodoList

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

const val LIST_KEY: String = "LIST_KEY"

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.todoListListView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val launchTodoListEditActivity = { id: Int? ->
            val intent = Intent(this, TodoListEditActivity::class.java)

            //thread {
                var editThisListId: Int = if (id == -1) {
                    db.TodoListDao().insert(
                            TodoList(
                                    null,
                                    "",
                                    ""
                            )
                    ).toInt()
                } else {
                    id!!
                }.toInt()
                intent.putExtra(LIST_KEY, editThisListId)
                Log.d("Butt", "The List ID passed to Edit Activity: " + editThisListId
                        + "//" + id)
            //}
            startActivity(intent)
        }

        createListButton.setOnClickListener { View ->
            launchTodoListEditActivity(-1)
        }

        val myAdapter = TodoListListAdapter(launchTodoListEditActivity)
        recyclerView.adapter = myAdapter

        ViewModelProviders.of(this)
                .get(MainViewModel::class.java)
                .getTodoLists()
                .observe(this, Observer {
                    if (it != null) {
                        myAdapter.list.clear()
                        myAdapter.list.addAll(it)
                        myAdapter.notifyDataSetChanged()
                    }
                })
    }
}
