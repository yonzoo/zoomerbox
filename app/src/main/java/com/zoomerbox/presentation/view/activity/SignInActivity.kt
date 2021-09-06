package com.zoomerbox.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.ActivitySignInBinding
import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.presentation.view.util.BundleKeys
import com.zoomerbox.presentation.viewmodel.SignInViewModel
import com.zoomerbox.presentation.viewmodel.SignInViewModelFactory
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.lang.ref.WeakReference
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    @Inject
    lateinit var viewModelFactory: SignInViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.errorText.visibility = View.GONE
        binding.waveLoadingView.visibility = View.GONE

        provideDependencies()
        createViewModel()
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.verifyPhoneBtn.setOnClickListener {
            val phoneNumber = "+7${binding.phoneNumberText.text}"
            viewModel.verifyPhoneNumber(phoneNumber, WeakReference(this))
        }
    }

    private fun setObservers() {
        viewModel.getResultLiveData().observe(this, {
            startActivity(MainActivity.newIntent(this))
        })
        viewModel.getProgressLiveData().observe(this, {
            if (it) {
                binding.waveLoadingView.visibility = View.VISIBLE
                binding.waveLoadingView.translationY = 1000F
                binding.waveLoadingView.alpha = 0F
                binding.waveLoadingView.startAnimation()
                binding.waveLoadingView
                    .animate()
                    .alpha(1.0F)
                    .translationY(0F).interpolator = LinearInterpolator()
            } else {
                binding.waveLoadingView.translationY = 0F
                binding.waveLoadingView.alpha = 1.0F
                binding.waveLoadingView
                    .animate()
                    .alpha(0F)
                    .translationY(1000F).interpolator = LinearInterpolator()
            }
        })
        viewModel.getErrorLiveData().observe(this, {
            Log.d("${TAG}_ERROR", it.message.toString())
            errorText.text = "Провалище! Проверьте номер телефона и попробуйте снова."
            errorText.visibility = View.VISIBLE
        })
        viewModel.getRedirectLiveData().observe(this, { verificationId ->
            val intent = SignInCodeActivity.newIntent(this).apply {
                val phoneNumber = "+7${binding.phoneNumberText.text}"
                putExtra(BundleKeys.VERIFICATION_ID, verificationId)
                putExtra(BundleKeys.PHONE_NUMBER, phoneNumber)
            }
            startActivity(intent)
        })
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignInViewModel::class.java)
    }

    private fun provideDependencies() {
        val activityComponent: ActivityComponent =
            ZoomerboxApplication.getAppComponent(this).getActivityComponent()
        activityComponent.inject(this)
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName

        fun newIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }
}