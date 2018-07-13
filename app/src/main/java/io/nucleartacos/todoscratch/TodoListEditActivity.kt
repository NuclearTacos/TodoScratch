package io.nucleartacos.todoscratch

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.nucleartacos.todoscratch.adapters.TodoListListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_todolist_edit.*

class TodoListEditActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist_edit)

        recyclerView = findViewById(R.id.todoListItemsView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        saveListButton.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            /*val intent = Intent(this, TodoListEditActivity::class.java)
            startActivity(intent)*/
        }

        val myAdapter = TodoListItemsAdapter()
        recyclerView.adapter = myAdapter
    }
}
