package com.zoomerbox.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.FragmentUserBinding
import com.zoomerbox.di.fragment.FragmentComponent
import com.zoomerbox.presentation.view.activity.AppSettingsActivity
import com.zoomerbox.presentation.view.activity.FavouriteActivity
import com.zoomerbox.presentation.view.activity.OrdersActivity
import com.zoomerbox.presentation.view.activity.SignInActivity
import com.zoomerbox.presentation.viewmodel.UserViewModel
import com.zoomerbox.presentation.viewmodel.UserViewModelFactory
import javax.inject.Inject

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserViewModel

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater)

        provideDependencies()
        createViewModel()
        setObservers()

        binding.goToFavouriteBtn.setOnClickListener {
            startActivity(FavouriteActivity.newIntent(requireContext()))
        }
        binding.gotToOrdersBtn.setOnClickListener {
            startActivity(OrdersActivity.newIntent(requireContext()))
        }
        binding.goToSettingsBtn.setOnClickListener {
            startActivity(AppSettingsActivity.newIntent(requireContext()))
        }
        binding.signOutBtn.setOnClickListener {
            viewModel.signOut()
        }

        viewModel.loadUser()

        return binding.root
    }

    private fun setObservers() {
        viewModel.getErrorLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(context, "Failed to get response from server", Toast.LENGTH_SHORT).show()
        }
        viewModel.getUserLiveData().observe(viewLifecycleOwner) { user ->
            if (user.avatarUrl.isNotEmpty()) {
                Picasso.get().load(user.avatarUrl).into(binding.userAvatar)
            }
            binding.userName.text = user.username
            binding.userPhone.text = user.phone
        }
        viewModel.getExitLiveData().observe(viewLifecycleOwner) {
            startActivity(SignInActivity.newIntent(requireContext()))
        }
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }

    private fun provideDependencies() {
        val fragmentComponent: FragmentComponent =
            ZoomerboxApplication.getAppComponent(requireContext()).getFragmentComponent()
        fragmentComponent.inject(this)
    }
}
