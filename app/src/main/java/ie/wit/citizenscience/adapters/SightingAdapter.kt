package ie.wit.citizenscience.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.citizenscience.databinding.CardSightingBinding
import ie.wit.citizenscience.models.SightingModel

interface SightingListener {
    fun onSightingClick(sighting: SightingModel)
}

class SightingAdapter constructor(private var sightings: List<SightingModel>,
                                    private val listener: SightingListener) :
    RecyclerView.Adapter<SightingAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSightingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val sighting = sightings[holder.adapterPosition]
        holder.bind(sighting, listener)
    }

    override fun getItemCount(): Int = sightings.size


    class MainHolder(private val binding : CardSightingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sighting: SightingModel, listener : SightingListener) {
            binding.sightingClassification.text = sighting.classification
            binding.sightingSpecies.text = sighting.species
            Picasso.get().load(sighting.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onSightingClick(sighting) }
        }
    }
}