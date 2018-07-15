package io.nucleartacos.todoscratch.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.nucleartacos.todoscratch.R
import io.nucleartacos.todoscratch.adapters.TodoListListAdapter
import io.nucleartacos.todoscratch.data.TodoList
import io.nucleartacos.todoscratch.db
import io.nucleartacos.todoscratch.viewmodels.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*

const val LIST_KEY: String = "LIST_KEY"

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    var deleteToggle: Boolean = false
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.todoListListView)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val launchTodoListEditActivity = { id: Int? ->
            val intent = Intent(this, TodoListEditActivity::class.java)
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
    } // onCreate
}
