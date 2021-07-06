package com.example.to_do_list_dio.datasource

import com.example.to_do_list_dio.model.Task

object TaskDataSource {

    private val list = arrayListOf<Task>()
    fun getList()= list.toList()

    fun insertTask(task: Task){
        if(task.id == 0){
            list.add(task.copy(id = list.size +1))
        }else{
            list.remove(task)
            list.add(task)
        }

    }
    fun findById(taskId: Int){
        list.find{
            it.id == taskId
        }
    }
    fun deleteTask(task:Task){
        list.remove(task)
    }

}