package com.ezioapps.elitenotes.update

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ezioapps.elitenotes.Db.models.Task
import com.ezioapps.elitenotes.R

import com.ezioapps.elitenotes.toast
import com.ezioapps.elitenotes.viewmodel.SharedViewModel
import com.ezioapps.elitenotes.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_update.description_et
import kotlinx.android.synthetic.main.fragment_update.spinner
import kotlinx.android.synthetic.main.fragment_update.todoTitle
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val viewModel : SharedViewModel by viewModels()
    private val mViewModel : TaskViewModel by viewModels()

//    private var _binding : FragmentUpdateBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
//        binding.args = args

        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)




        view.todoTitle.setText(args.taskCurrent.title)
        view.description_et.setText(args.taskCurrent.body)
        view.spinner.setSelection(args.taskCurrent.priority.ordinal)
        view.spinner.onItemSelectedListener = viewModel.listener

//        arguments?.let {
//            val task = UpdateFragmentArgs.fromBundle(it).taskCurrent
//            view.todoTitle.setText(task.title)
//            view.description_et.setText(task.body)
//            view.spinner.setSelection(task.priority.ordinal)
//            view.spinner.onItemSelectedListener = viewModel.listener
//        }

        return view
       // return binding.root
    }


    private fun updateTaskToDB(){

        val title = todoTitle?.text.toString()
        val body = description_et?.text.toString()
        val priority = viewModel.parseToPr(spinner?.selectedItem.toString())
        if (!viewModel.verify(title, body)){

            todoTitle.error = "either of this is empty"
            description_et.error = "either of this is empty"
            todoTitle.requestFocus()
            description_et.requestFocus()
        }
        else{
            val task = Task(args.taskCurrent.id, title, priority , body)
            mViewModel.updateTask(task)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            context?.toast("update to database successful")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update_done_menu -> updateTaskToDB()
            R.id.update_delete_menu -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {

        AlertDialog.Builder(requireContext()).setTitle("Delete ${args.taskCurrent.title}?")
            .setMessage("Are You Sure you want to delete? You cant undo this action!")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                mViewModel.deleteTask(args.taskCurrent)
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            }).show()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}