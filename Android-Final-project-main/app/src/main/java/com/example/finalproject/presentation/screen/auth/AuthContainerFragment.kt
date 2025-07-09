package com.example.finalproject.presentation.screen.auth

import com.example.finalproject.databinding.FragmentAuthContainerBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class AuthContainerFragment : BaseFragment<FragmentAuthContainerBinding>(FragmentAuthContainerBinding::inflate) {

    override fun setUp() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            tab.text = when(position) {
                0 -> "Sign In"
                1 -> "Sign Up"
                else -> throw IllegalStateException("Invalid position")
            }
        }.attach()
    }

}