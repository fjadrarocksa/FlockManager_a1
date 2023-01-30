package com.example.flockmanager_a1

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.google.android.material.textfield.TextInputEditText
import java.util.*

@SuppressLint("RestrictedApi")
class AddFlockActivity : AppCompatActivity() {

    private lateinit var addFlockBackground: ConstraintLayout
    private lateinit var addFlockWindowBg: ConstraintLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flock)

        addFlockBackground = findViewById(R.id.add_bird_background)
        addFlockWindowBg = findViewById(R.id.add_bird_window_bg)

        setActivityStyle()

        val dateAdded = intent.getSerializableExtra("date_added") as? Date
        val flockName: TextInputEditText = findViewById<TextInputEditText>(R.id.flock_name_input)

        // TODO - Is this needed?
        /*
        val data = Intent()
        data.putExtra("date_added", dateAdded)
        setResult(Activity.RESULT_OK, data)
         */

        val addFlockButton = findViewById<Button>(R.id.create_flock_button)
        addFlockButton.setOnClickListener {
            val data = Intent()
            data.putExtra("date_added", dateAdded)
            data.putExtra("flock_name", flockName.toString())
            Log.w("AddFlockActivity", "addFlockButton.setOnClickListener")
            setResult(Activity.RESULT_OK, data)
            // Close current window
            onBackPressed()
        }

    }

    private fun setActivityStyle() {
        // Make the background full screen, over status bar
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        this.window.statusBarColor = Color.TRANSPARENT
        val winParams = this.window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        this.window.attributes = winParams

        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            addFlockBackground.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        addFlockWindowBg.alpha = 0f
        addFlockWindowBg.animate().alpha(1f).setDuration(500)
            .setInterpolator(DecelerateInterpolator()).start()

        // Close window when you tap on the dim background
        addFlockBackground.setOnClickListener { onBackPressed() }
        addFlockWindowBg.setOnClickListener { /* Prevent activity from closing when you tap on the popup's window background */ }
    }


    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            addFlockBackground.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        // Fade animation for the Popup Window when you press the back button
        addFlockWindowBg.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }
}