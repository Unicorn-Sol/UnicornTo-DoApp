package com.ezioapps.elitenotes

import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.ezioapps.elitenotes.Db.models.Priority
import com.ezioapps.elitenotes.Db.models.Task
import com.ezioapps.elitenotes.list.ListFragmentDirections

class BindingAdapters {

    companion object{

//        @BindingAdapter("android:navigate")
//        @JvmStatic
//        fun navigateToAddFragment(view: View, navigate : Boolean){
//            view.setOnClickListener {
//                it.findNavController().navigate(R.id.action_listFragment_to_addFragment)
//            }
//        }

//        @BindingAdapter("android:emptyDb")
//        @JvmStatic
//        fun emptyDb( view: View, emptyDb : MutableLiveData<Boolean>){
//            when(emptyDb.value){
//                true -> view.visibility = View.VISIBLE
//                false -> view.visibility = View.GONE
//            }
//        }

        @BindingAdapter("android:toPriority")
        @JvmStatic
        fun toPriority(view: Spinner, priority: Priority){
            when(priority.ordinal){
                0 -> {view.setSelection(0)}
                1 -> {view.setSelection(1)}
                2 -> {view.setSelection(2)}
            }
        }

        @BindingAdapter("android:cardColor")
        @JvmStatic
        fun cardColor(view : CardView, priority: Priority){
            when(priority.ordinal){
                0 -> view.setCardBackgroundColor(ContextCompat.getColor( view.context,R.color.red))
                1 -> view.setCardBackgroundColor(ContextCompat.getColor( view.context,R.color.yellow))
                2 -> view.setCardBackgroundColor(ContextCompat.getColor( view.context,R.color.green))
            }
        }
        @BindingAdapter("android:goToUpdate")
        @JvmStatic
        fun gotoUpdate(view: ViewGroup, task: Task){
            view.setOnClickListener {
            view.findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(task))
            }
        }
    }
}