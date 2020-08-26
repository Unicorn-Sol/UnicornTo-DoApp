package com.ezioapps.elitenotes

import androidx.recyclerview.widget.DiffUtil
import com.ezioapps.elitenotes.Db.models.Task

class TaskDiffUtil(
    private val oldList: List<Task>,
    private val newList: List<Task>
):DiffUtil.Callback() {
    override fun getOldListSize()= oldList.size
    override fun getNewListSize()= newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition].id == newList[newItemPosition].id
                &&oldList[oldItemPosition].title == newList[newItemPosition].title
                &&oldList[oldItemPosition].body == newList[newItemPosition].body
                &&oldList[oldItemPosition].priority == newList[newItemPosition].priority
    }
}