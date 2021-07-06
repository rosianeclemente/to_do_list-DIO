package com.example.to_do_list_dio.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.to_do_list_dio.databinding.ActivityMainBinding
import com.example.to_do_list_dio.datasource.TaskDataSource
import com.example.to_do_list_dio.ui.AddTaskActivity.Companion.TASK_ID

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    private val adapter by lazy { TaskListAdapter()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvTask.adapter = adapter


        insertListeners()
    }

    private fun insertListeners() {
        binding.floButton.setOnClickListener{
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java )
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }
        adapter.listenerDelete ={
           TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == RESULT_OK) updateList()


    }
    private  fun updateList(){
       val list= TaskDataSource.getList()
        if(list.isEmpty()){
            binding.image.emptyState.visibility = View.VISIBLE
        }else{
            View.GONE
        }
        adapter.submitList(list)

    }

    companion object{
        private const val  CREATE_NEW_TASK = 1000
    }
}