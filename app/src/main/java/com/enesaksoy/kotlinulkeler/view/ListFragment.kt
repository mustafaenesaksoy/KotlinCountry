package com.enesaksoy.kotlinulkeler.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesaksoy.kotlinulkeler.adapter.CountryAdapter
import com.enesaksoy.kotlinulkeler.databinding.FragmentListBinding
import com.enesaksoy.kotlinulkeler.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewmodel : ListViewModel
    private var adapter = CountryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewmodel.refreshdata()
        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = adapter

        binding.swiperefreshlayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility =  View.VISIBLE
            viewmodel.apidencek()
            binding.swiperefreshlayout.isRefreshing = false
        }
        observeLiveData()
    }

    private fun observeLiveData(){
        viewmodel.countries.observe(viewLifecycleOwner){
            it?.let {
                binding.countryList.visibility = View.VISIBLE
                adapter.updateCounryList(it)
            }
        }
        viewmodel.countryError.observe(viewLifecycleOwner){
            it?.let {
                if(it){
                    binding.countryError.visibility = View.VISIBLE
                }else{
                    binding.countryError.visibility = View.GONE
                }
            }
        }
        viewmodel.countryLoading.observe(viewLifecycleOwner){
            it?.let {
                if(it){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryLoading.visibility = View.GONE
                } else{
                    binding.countryLoading.visibility = View.GONE
                }
            }
        }
    }
}