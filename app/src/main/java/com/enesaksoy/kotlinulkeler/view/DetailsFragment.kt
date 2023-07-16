package com.enesaksoy.kotlinulkeler.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.enesaksoy.kotlinulkeler.databinding.FragmentDetailsBinding
import com.enesaksoy.kotlinulkeler.util.downloadfromUrl
import com.enesaksoy.kotlinulkeler.util.placeHolderProgressBar
import com.enesaksoy.kotlinulkeler.viewmodel.DetailsViewModel


class DetailsFragment : Fragment() {

    private var countryuuid = 0
    private lateinit var viewmodel : DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        arguments?.let {
            countryuuid = DetailsFragmentArgs.fromBundle(it).countryUUID
        }
        viewmodel.getdataFromRoom(countryuuid)

        observeLiveData()
    }

    private fun observeLiveData(){
        viewmodel.countrylivedata.observe(viewLifecycleOwner){
            it?.let {country ->
                binding.countryname.text = country.countryName
                binding.countryregion.text = country.countryRegion
                binding.countrylanguage.text = country.countryLanguage
                binding.countrycapital.text = country.countryCapital
                binding.countrycurrency.text = country.countryCurrency
                context?.let {
                    binding.countryimage.downloadfromUrl(country.imageUrl, placeHolderProgressBar(it))
                }
            }
        }
    }
}