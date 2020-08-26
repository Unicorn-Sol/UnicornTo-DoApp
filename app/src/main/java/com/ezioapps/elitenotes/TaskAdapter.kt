package com.ezioapps.elitenotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ezioapps.elitenotes.Db.models.Task
import com.ezioapps.elitenotes.databinding.TaskRecyclerListBinding
import com.ezioapps.elitenotes.list.ListFragmentDirections
import kotlinx.android.synthetic.main.task_recycler_list.view.*

//class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
//
//    private var tasks = emptyList<Task>()
//
//    class TaskViewHolder(private val binding : TaskRecyclerListBinding) : RecyclerView.ViewHolder(binding.root){
//
//        fun bind(task : Task){
//            binding.task = task
//            binding.executePendingBindings()
//        }
//
//        companion object{
//            fun from(parent: ViewGroup) : TaskViewHolder{
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = TaskRecyclerListBinding.inflate(layoutInflater, parent, false)
//                return TaskViewHolder(binding)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
//        return TaskViewHolder.from(parent)
//    }
//
//    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        holder.bind(tasks[position])
//    }
//
//    override fun getItemCount()=  tasks.size
//
//    fun setData(tasks : List<Task>){
//        this.tasks = tasks
//        notifyDataSetChanged()
//    }
//
//}

//    Code without using data binding with recycler layout.


class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var tasks = emptyList<Task>()

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_recycler_list, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val task = tasks[position]

        holder.itemView.title_recycler_text.text = task.title
        holder.itemView.body_recycler_text.text  = task.body
        holder.itemView.task_item_in_list.setOnClickListener {
            it.findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(task))
        }
        when(task.priority.ordinal.toString()){
            "0" -> holder.itemView.colour_indicator_circle.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            "1" -> holder.itemView.colour_indicator_circle.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
            "2" -> holder.itemView.colour_indicator_circle.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
    }

    override fun getItemCount()=  tasks.size

    fun setData(tasks2 : List<Task>){
       val taskDiffUtil = TaskDiffUtil(tasks,tasks2)
       val taskDiffResult = DiffUtil.calculateDiff(taskDiffUtil)
        this.tasks = tasks2
//        notifyDataSetChanged()
       taskDiffResult.dispatchUpdatesTo(this)
    }

}