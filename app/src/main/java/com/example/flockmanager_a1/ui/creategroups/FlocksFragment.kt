package com.example.flockmanager_a1.ui.creategroups

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flockmanager_a1.*
import com.example.flockmanager_a1.databinding.FragmentFlocksBinding
import com.example.flockmanager_a1.db.Chicken
import com.example.flockmanager_a1.db.Flock
import com.example.flockmanager_a1.db.FlocksDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

class FlocksFragment : Fragment() {

    private var _binding: FragmentFlocksBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FlocksRVAdapter
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val flocksDatabase by lazy { FlocksDatabase.getDatabase(binding.root.context, applicationScope).flockDao() }

    // This property is only valid between onCreateView and
    // onDestroyView.

    //private val newWordActivityRequestCode = 1

    /* =================
    private val flockViewModel: FlockViewModel by viewModels {
        FlockViewModelFactory((activity?.applicationContext as FlockManagerApplication).flockRepository)
    }

    private var launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.w(tag,"FlocksFragment::registerForActivityResult")
            //result.data?.getStringExtra(AddFlockActivity.EXTRA_REPLY)?.let { reply ->
             val flock = Flock(Date(), "Dasset",0,0,"Bladisar")
             flockViewModel.insert(flock)
        }
        else {
            Toast.makeText(
                activity?.applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
     =================== */


    val editFlockResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                Log.w(tag,"FlocksFragment::editFlockResultLauncher")
                // Get the edited note from the AddNoteActivity
                val dateAdded = result.data?.getSerializableExtra("date_added") as Date
                val flockName = result.data?.getStringExtra("flock_name")
                //val sexText = result.data?.getStringExtra("sex")
                // Update the note in the list

                val editedFlock = Flock(
                    dateAdded,
                    flockName ?: "",
                    0,
                    0,
                    "edited..")

                lifecycleScope.launch {
                    //flockViewModel.update(editedFlock)
                    flocksDatabase.updateFlock(editedFlock)
                }
            }
        }

    private val newFlockResultsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Get the new note from the AddNoteActivity
                Log.w("FlocksFragment","newFlockResultLauncher")

                val dateAdded = Date()
                val flockName = result.data?.getStringExtra("flock_name")
                // Add the new note at the top of the list
                val newFlock = Flock(
                    dateAdded,
                    flockName ?: "",
                    0,
                    0,
                    "new..")


                lifecycleScope.launch {
                    flocksDatabase.addFlock(newFlock)
                }
                Log.w(tag,"newFlockResultsLauncher - after addFlock(newFlock)")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View {

        _binding = FragmentFlocksBinding.inflate(inflater, container, false)


        setRecyclerView()

        observeFlocks()

        val fab = binding.fab
        fab.setOnClickListener {
            val intent = Intent(binding.root.context, AddFlockActivity::class.java)
            newFlockResultsLauncher.launch(intent)
        }
        val root: View = binding.root
        return root
    }

    private fun setRecyclerView() {
        val flocksRecyclerview = binding.flocksRecyclerview //findViewById<RecyclerView>(R.id.flocks_recyclerview)
        flocksRecyclerview.layoutManager = LinearLayoutManager(activity)
        flocksRecyclerview.setHasFixedSize(true)
        adapter = FlocksRVAdapter()
        adapter.setItemListener(object : RecyclerClickListener {
            // Tap the 'X' to delete the note.
            override fun onItemRemoveClick(position: Int) {
                val flockList = adapter.currentList.toMutableList()
                val flockName = flockList[position].flockName
                val flockDateAdded = flockList[position].dateAdded
                //val chickenSex = chickensList[position].sexText
                val removeFlock = Flock(flockDateAdded, flockName, 0, 0, "")
                flockList.removeAt(position)
                adapter.submitList(flockList)
                lifecycleScope.launch {
                    flocksDatabase.deleteFlock(removeFlock)
                }
            }

            // Tap the note to edit.
            override fun onItemClick(position: Int) {
                val intent = Intent(activity, AddFlockActivity::class.java)
                val flocksList = adapter.currentList.toMutableList()
                intent.putExtra("date_added", flocksList[position].dateAdded)
                intent.putExtra("flock_name", flocksList[position].flockName)
                //intent.putExtra("sex", chickensList[position].sexText)
                //intent.putExtra("group1",chickensList[position].groupId)
                editFlockResultLauncher.launch(intent)
            }
        })
        flocksRecyclerview.adapter = adapter
    }

    private fun observeFlocks() {
        lifecycleScope.launch {
            flocksDatabase.getAll().collect { flocksList ->
                if (flocksList.isNotEmpty()) {
                    adapter.submitList(flocksList)
                }
            }
        }
    }



}






        //view?.setBackgroundColor(Color.GRAY)


        /*
        val flockRecyclerview = binding.flocksRecyclerview
        val adapter = FlocksRVAdapter()
        flockRecyclerview.adapter = adapter
        flockRecyclerview.layoutManager = LinearLayoutManager(context)
        //flockRecyclerview.setHasFixedSize(true)

        flockViewModel.allFlocks.observe(viewLifecycleOwner)  { flocks ->
            // Update the cached copy of the words in the adapter.
            flocks?.let { adapter.setFlocks(it)}
        }

        val fab = binding.fab
        fab.setOnClickListener {
            val intent = Intent(context, AddFlockActivity::class.java)
            launcher.launch(intent)
        }

        adapter.setItemListener(object : RecyclerClickListener {
            // Tap the 'X' to delete the note.
            override fun onItemRemoveClick(position: Int) {
                Log.w(tag, "FlockFragment::onCreateView::onItemRemoveClick")
                TODO("Not yet implemented")
            }

            override fun onItemClick(position: Int) {
                Log.w(tag, "FlockFragment::onCreateView::onItemClick")
                val intent = Intent(activity, AddFlockActivity::class.java)
                val flockList = adapter.currentList.toMutableList()
                intent.putExtra("date_added", flockList[position].dateAdded)
                intent.putExtra("flock_name", flockList[position].flockName)

                editFlockResultLauncher.launch(intent)
            }
        })



        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_note_menu_item) {
            Log.w(tag,"FlocksFragment::onOptionsItemSelected")
            // Open AddFlockActivity
            val intent = Intent(activity, AddFlockActivity::class.java)
            newFlockResultsLauncher.launch(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
/*
    private fun observeChickens() {
        lifecycleScope.launch {
            val flockList = flockViewModel.allFlocks
            if (flockList.isNotEmpty()) {
                    adapter.submitList(flockList)
                } }
            /*
            flocksDatabase.getAllChickens().collect { flockList ->
                if (flockList.isNotEmpty()) {
                    adapter.submitList(flockList)
                }
            }
             */
        }
    }

 */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()

    }
    */


*/

