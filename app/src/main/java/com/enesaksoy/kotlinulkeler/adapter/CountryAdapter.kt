package com.enesaksoy.kotlinulkeler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.enesaksoy.kotlinulkeler.R
import com.enesaksoy.kotlinulkeler.view.ListFragmentDirections
import com.enesaksoy.kotlinulkeler.databinding.RecyclerRowBinding
import com.enesaksoy.kotlinulkeler.model.Country
import com.enesaksoy.kotlinulkeler.util.downloadfromUrl
import com.enesaksoy.kotlinulkeler.util.placeHolderProgressBar

class CountryAdapter(var countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryHolder>(){


    class CountryHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        /*holder.binding.name.text = countryList.get(position).countryName
        holder.binding.region.text = countryList.get(position).countryRegion
        holder.itemView.setOnClickListener {

        }
        holder.binding.imageview.downloadfromUrl(countryList.get(position).imageUrl,
            placeHolderProgressBar(holder.itemView.context)
        )*/
        holder.binding.country = countryList.get(position)

        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment().setCountryUUID(countryList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }
    fun updateCounryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}