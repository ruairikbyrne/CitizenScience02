package ie.wit.citizenscience.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.citizenscience.databinding.CardSightingBinding
import ie.wit.citizenscience.models.SightingModel

interface SightingClickListener {
    fun onSightingClick(sighting: SightingModel)
}

class SightingAdapter constructor(private var sightings: ArrayList<SightingModel>, private val listener: SightingClickListener) :
    RecyclerView.Adapter<SightingAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSightingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val sighting = sightings[holder.adapterPosition]
        holder.bind(sighting,listener)

    }

    fun removeAt(position: Int) {
        sightings.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = sightings.size


    inner class MainHolder(val binding : CardSightingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sighting: SightingModel, listener: SightingClickListener) {
            //binding.sightingClassification.text = sighting.classification
            //binding.sightingSpecies.text = sighting.species
            binding.root.tag = sighting
            binding.sighting = sighting
            Picasso.get().load(sighting.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onSightingClick(sighting) }
            binding.executePendingBindings()

        }


    }


}