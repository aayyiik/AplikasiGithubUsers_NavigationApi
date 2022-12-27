package com.submission.picodiploma.aplikasigithubusers_navigationapi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.picodiploma.aplikasigithubusers_navigationapi.adapter.UsersAdapter
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.ItemsItem
import com.submission.picodiploma.aplikasigithubusers_navigationapi.databinding.FragmentFollowsBinding
import com.submission.picodiploma.aplikasigithubusers_navigationapi.utils.MyResult
import com.submission.picodiploma.aplikasigithubusers_navigationapi.viewmodel.DetailViewModel


class FollowsFragment : Fragment() {

    private var binding: FragmentFollowsBinding? = null
    private val adapter by lazy{
        UsersAdapter{

        }
    }

    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowsFragment.adapter

        }

        when (type){
            FOLLOWERS -> {
             viewModel.resultFollowersUser.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            FOLLOWING -> {
                viewModel.resultFollowingUser.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    private fun manageResultFollows(foll: MyResult){
        when(foll){
            is MyResult.Success<*> -> {
                adapter.setData(foll.list as MutableList<ItemsItem>)
            }
            is MyResult.Loading -> {
                binding?.progressBar?.isVisible = foll.isLoading
            }
            is MyResult.Error -> {
                Toast.makeText(requireActivity(), foll.exception.message.toString(),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object{
        const val FOLLOWING = 100
        const val FOLLOWERS = 101
        fun newInstance(type: Int) = FollowsFragment().apply {
            this.type = type
        }
    }

}