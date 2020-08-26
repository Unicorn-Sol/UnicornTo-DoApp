package com.ezioapps.elitenotes.add

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ezioapps.elitenotes.Db.models.Task
import com.ezioapps.elitenotes.R
import com.ezioapps.elitenotes.toast
import com.ezioapps.elitenotes.viewmodel.SharedViewModel
import com.ezioapps.elitenotes.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by viewModels()
    // private lateinit var viewModel : TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        view.spinner.onItemSelectedListener = sharedViewModel.listener
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== R.id.menu_add_done){
            addTaskToDB()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addTaskToDB(){

        val title = todoTitle?.text.toString()
        val body = description_et?.text.toString()
        val priority = sharedViewModel.parseToPr(spinner?.selectedItem.toString())
        if (!sharedViewModel.verify(title, body)){

            todoTitle.error = "either of this is empty"
            description_et.error = "either of this is empty"
            todoTitle.requestFocus()
            description_et.requestFocus()
        }
        else{
            val task = Task(0, title, priority , body)
            viewModel.insertTask(task)
           findNavController().navigate(R.id.action_addFragment_to_listFragment)
            context?.toast("insert to database successful")
        }
    }


}