package com.nbtec.newfitnesstracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.imc,
                txtStringId = R.string.imc,
                color = R.color.green_transparent
            )
        )
        val tmb = mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.tmb,
                txtStringId = R.string.tmb,
                color = R.color.green_transparent
            )
        )
        val music = mainItems.add(
            MainItem(
                id = 3,
                drawableId = R.drawable.music,
                txtStringId = R.string.favorite_music,
                color = R.color.green_transparent
            )
        )
        val motivation = mainItems.add(
            MainItem(
                id = 4,
                drawableId = R.drawable.motivacao,
                txtStringId = R.string.motivation,
                color = R.color.green_transparent
            )
        )

        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this@MainActivity, TmbActivity::class.java)
                    startActivity(intent)
                }
//                3 -> {
//                    val intent = Intent(this@MainActivity, MusicActivity::class.java)
//                    startActivity(intent)
//                }
//                4 -> {
//                    val intent = Intent(this@MainActivity, MotivationActivity::class.java)
//                    startActivity(intent)
//                }
            }

        }

        rvMain = findViewById(R.id.rv_Main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }

    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit,
    ) :
        RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {
            return mainItems.size
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val viewImg: ImageView = itemView.findViewById(R.id.item_view_icon)
                val viewTxt: TextView = itemView.findViewById(R.id.item_view_txt)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                viewImg.setImageResource(item.drawableId)
                viewTxt.setText(item.txtStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)

                }
            }
        }
    }
}
