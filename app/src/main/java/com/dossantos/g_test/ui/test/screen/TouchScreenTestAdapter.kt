package com.dossantos.g_test.ui.test.screen

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dossantos.g_test.model.test.screen.TouchScreenTesterModel

class TouchScreenTestAdapter(
    val touchItemsList: List<TouchScreenTesterModel>
) : RecyclerView.Adapter<TouchScreenTestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TouchScreenTestViewHolder.inflate(parent)

    override fun getItemCount() = touchItemsList.size

    override fun onBindViewHolder(holder: TouchScreenTestViewHolder, position: Int) {
        holder.onBind(touchItemsList[position])
    }
}