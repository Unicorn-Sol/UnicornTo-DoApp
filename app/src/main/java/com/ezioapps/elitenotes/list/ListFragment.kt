package com.ezioapps.elitenotes.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ezioapps.elitenotes.Db.models.Task
import com.ezioapps.elitenotes.R
import com.ezioapps.elitenotes.TaskAdapter
import com.ezioapps.elitenotes.databinding.FragmentListBinding
import com.ezioapps.elitenotes.toast
import com.ezioapps.elitenotes.viewmodel.SharedViewModel
import com.ezioapps.elitenotes.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel : TaskViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by viewModels()

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter : TaskAdapter by lazy { TaskAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel


        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_list, container, false)

        setRecyclerView()

        viewModel.getAllData.observe(viewLifecycleOwner, Observer {
            sharedViewModel.checkIfDatabaseIsEmpty(it)
            adapter.setData(it)
        })

        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyOrList(it)
        })


        binding.floatingActionButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        swipeToDelete(binding.recyclerView)
        swipeToUpdate(binding.recyclerView)
        return binding.root
    }

    private fun swipeToUpdate(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val taskToUpdate = adapter.tasks[viewHolder.adapterPosition]
                viewHolder.itemView.findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(taskToUpdate))
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val taskToDelete = adapter.tasks[viewHolder.adapterPosition]
               viewModel.deleteTask(taskToDelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                //requireContext().toast("Successfully deleted : ${taskToDelete.title}")
                restoreDeletedTask(viewHolder.itemView, taskToDelete, viewHolder.adapterPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.todo_list_menu, menu)
        val search = menu.findItem(R.id.app_bar_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
           R.id.menu_delete_all -> deleteAllTasks()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showEmptyOrList(emptyDatabase : Boolean){
        if(emptyDatabase){
             view?.no_data_imageView?.visibility = View.VISIBLE
            view?.no_data_textView?.visibility = View.VISIBLE
        }else{
            view?.no_data_imageView?.visibility = View.GONE
            view?.no_data_textView?.visibility = View.GONE
        }
    }

    private fun deleteAllTasks() {
        AlertDialog.Builder(requireContext()).setTitle("Delete All Tasks?")
            .setMessage("Are You Sure you want to delete? You cant undo this action!")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                viewModel.deleteAll()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            }).show()
    }

    private fun setRecyclerView(){
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
    }

    private fun restoreDeletedTask(view:View, deletedTask : Task, position:Int){
        Snackbar.make(view, "${deletedTask.title} Deleted!",Snackbar.LENGTH_LONG)
            .setAction("Undo"){
                viewModel.insertTask(deletedTask)
//                adapter.notifyItemChanged(position)
            }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query2: String) {

        val query = "%$query2%"
        viewModel.searchDatabase(query).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it)
            }
        })
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchThroughDatabase(query)
        }
        return true
    }
}