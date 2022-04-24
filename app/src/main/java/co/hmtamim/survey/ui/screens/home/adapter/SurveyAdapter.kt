package co.hmtamim.survey.ui.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.hmtamim.survey.databinding.ItemSurveyBinding
import co.hmtamim.survey.domain.model.SurveyModel
import kotlin.properties.Delegates

class SurveyAdapter() :
    RecyclerView.Adapter<SurveyAdapter.ViewHolder>(), DiffUpdateAdapter {

    var items: List<SurveyModel> by Delegates.observable(listOf()) { _, old, new ->
        autoNotify(
            old,
            new,
            { oldItem, newItem -> oldItem.id == newItem.id },
            { oldItem, newItem ->
                oldItem.title == newItem.title && oldItem.description == newItem.description
            }
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSurveyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemSurveyBinding) : RecyclerView.ViewHolder(binding.root)
}