package com.example.flockmanager_a1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import com.example.flockmanager_a1.databinding.ActivityNewChickenBinding

class NewChickenActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var binding: ActivityNewChickenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewChickenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editWordView = binding.editWord

        val button = binding.buttonSave
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }

}