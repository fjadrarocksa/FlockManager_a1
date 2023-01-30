package com.example.flockmanager_a1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flockmanager_a1.db.FlocksDatabase
import com.example.flockmanager_a1.db.Chicken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*


class BirdsActivity : AppCompatActivity() {

    private lateinit var adapter: BirdsRVAdapter

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val flocksDatabase by lazy {
        FlocksDatabase.getDatabase(this, applicationScope).chickenDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birds)

        setRecyclerView()

        observeChickens()
    }

    private fun setRecyclerView() {
        val birdsRecyclerview = findViewById<RecyclerView>(R.id.flocks_recyclerview)
        birdsRecyclerview.layoutManager = LinearLayoutManager(this)
        birdsRecyclerview.setHasFixedSize(true)
        adapter = BirdsRVAdapter()
        adapter.setItemListener(object : RecyclerClickListener {


            // Tap the 'X' to delete the note.
            override fun onItemRemoveClick(position: Int) {
                val chickensList = adapter.currentList.toMutableList()
                val chickenText = chickensList[position].breedText
                val chickenDateAdded = chickensList[position].dateAdded
                val chickenSex = chickensList[position].sexText
                val removeChicken = Chicken(
                    chickenDateAdded,
                    chickenText,
                    chickenSex,
                    "group1")
                chickensList.removeAt(position)
                adapter.submitList(chickensList)
                lifecycleScope.launch {
                    flocksDatabase.deleteChicken(removeChicken)
                }
            }

            // Tap the note to edit.
            override fun onItemClick(position: Int) {
                val intent = Intent(this@BirdsActivity, AddBirdActivity::class.java)
                val chickensList = adapter.currentList.toMutableList()
                intent.putExtra("date_added", chickensList[position].dateAdded)
                intent.putExtra("breed", chickensList[position].breedText)
                intent.putExtra("sex", chickensList[position].sexText)
                intent.putExtra("group1",chickensList[position].groupId)
                editChickenResultLauncher.launch(intent)
            }
        })
        birdsRecyclerview.adapter = adapter
    }

    private fun observeChickens() {
        lifecycleScope.launch {
            flocksDatabase.getAllChickens().collect { chickensList ->
                if (chickensList.isNotEmpty()) {
                    adapter.submitList(chickensList)
                }
            }
        }
    }

    private val newChickenResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Get the new note from the AddNoteActivity
                Log.w("BirdsActivity","newChickenResultLauncher")

                val dateAdded = Date()
                val breedText = result.data?.getStringExtra("breed")
                val sexText = result.data?.getStringExtra("sex")
                // Add the new note at the top of the list
                val newChicken = Chicken(
                    dateAdded,
                    breedText ?: "",
                    sexText ?: "",
                    "group1")
                Log.w("BirdsActivity","val newChicken")
                lifecycleScope.launch {
                    flocksDatabase.addChicken(newChicken)
                }
            }
        }

    val editChickenResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Get the edited note from the AddNoteActivity
                val dateAdded = result.data?.getSerializableExtra("date_added") as Date
                val breedText = result.data?.getStringExtra("breed")
                val sexText = result.data?.getStringExtra("sex")
                // Update the note in the list
                val editedChicken = Chicken(
                    dateAdded,
                    breedText ?: "",
                    sexText ?: "",
                    "group1")
                lifecycleScope.launch {
                    flocksDatabase.updateChicken(editedChicken)
                }
            }
        }


    // The '+' menu button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_note_menu_item) {
            // Open AddBirdActivity
            val intent = Intent(this, AddBirdActivity::class.java)
            newChickenResultLauncher.launch(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notes, menu)
        return true
    }
}