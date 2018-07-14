package io.nucleartacos.todoscratch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import io.nucleartacos.todoscratch.data.TodoList
import kotlinx.android.synthetic.main.activity_todolist_edit.*
import kotlin.concurrent.thread

class TodoListEditActivity : AppCompatActivity() {

    val TAG: String = "TodoListEditActivity: "

    lateinit var itemsRecyclerView: RecyclerView
    private var todoListId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist_edit)

        val titleEditText = findViewById<EditText>(R.id.listTitleEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)

        todoListId = intent.getIntExtra(LIST_KEY, -1)
        Log.d("Butt", "TodoListEditActivity: todoListId: "+ todoListId)

        thread {

            var todoList = db.TodoListDao().getById(todoListId)

            titleEditText.setText(todoList.title)
            descriptionEditText.setText(todoList.description)
        }

        itemsRecyclerView = findViewById(R.id.todoListItemsView)
        itemsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        saveListButton.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            saveListChanges(titleEditText, descriptionEditText)
            Log.d("butt", "Toasty")
            /*val intent = Intent(this, TodoListEditActivity::class.java)
            startActivity(intent)*/
        }

        val myAdapter = TodoListItemsAdapter(todoListId)
        itemsRecyclerView.adapter = myAdapter
    }

    fun saveListChanges(titleEditText: EditText, descriptionEditText: EditText) {
        Log.d("butt","Toasted: "+ todoListId)
        var todoList: TodoList = if (todoListId != -1){
            TodoList(
                todoListId,
                titleEditText.text.toString(),
                descriptionEditText.text.toString()
            )
        } else {
            TodoList(
                null,
                titleEditText.text.toString(),
                descriptionEditText.text.toString()
            )
        }
        thread {
            var insertedList: Int = db.TodoListDao().insert(todoList).toInt()
            Log.d("butt","Toasted Again: "+ insertedList)


            // You neeed to save back the current state of the title/description to the list.  Rememmber you ahve access to the id of the list
        }
    }
}
