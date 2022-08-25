package com.dreamgyf.android.ui.rv

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)
        rv.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val view = View(this@MainActivity)
                view.background = GradientDrawable().apply {
                    color = ColorStateList.valueOf(Color.GRAY)
                    cornerRadius = 50f
                }
                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1000
                )
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            }

            override fun getItemCount(): Int {
                return 10
            }

        }
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.addItemDecoration(SpaceItemDecoration(50, 30))
    }
}