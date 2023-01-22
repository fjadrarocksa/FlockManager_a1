package com.example.flockmanager_a1.ui.creategroups

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.flockmanager_a1.ChickenListAdapter
import com.example.flockmanager_a1.FlockManagerApplication
import com.example.flockmanager_a1.NewChickenActivity
import com.example.flockmanager_a1.R
import com.example.flockmanager_a1.databinding.FragmentCreateGroupsBinding
import com.example.flockmanager_a1.db.Chicken
import com.example.flockmanager_a1.db.FlockDatabase

class CreateGroupsFragment : Fragment() {

    private var _binding: FragmentCreateGroupsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val newWordActivityRequestCode = 1

    private val createGroupsViewModel: CreateGroupsViewModel by viewModels {
        CreateGroupsViewModelFactory((activity?.applicationContext as FlockManagerApplication).repository)
    }

    private var launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra(NewChickenActivity.EXTRA_REPLY)?.let { reply ->
                val chicken = Chicken(3, reply, "apa")
                createGroupsViewModel.insert(chicken)
            }
        }
        else {
            Toast.makeText(
                activity?.applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View {

//        val createGroupsViewModel =
//            ViewModelProvider(this).get(CreateGroupsViewModel::class.java)

        _binding = FragmentCreateGroupsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        view?.setBackgroundColor(Color.GRAY)

        ///
        val recyclerView = binding.recyclerview //findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ChickenListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        createGroupsViewModel.allChickens.observe(viewLifecycleOwner)  { chickens ->
            // Update the cached copy of the words in the adapter.
            chickens?.let { adapter.submitList(it) }
        }

        val fab = binding.fab
        fab.setOnClickListener {
            val intent = Intent(context, NewChickenActivity::class.java)
            //openSomeActivityForResult(intent)
            launcher.launch(intent)
            //startActivityForResult(intent, newWordActivityRequestCode)
        }
        ////



        /*
        val birdTypeSpinner: Spinner = binding.birdTypeSpinner
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.bird_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            birdTypeSpinner.adapter = adapter
        }

        val birdBreedSpinner: Spinner = binding.birdBreedSpinner
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.chicken_breeds,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            birdBreedSpinner.adapter = adapter
        }
         */

        return root

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}