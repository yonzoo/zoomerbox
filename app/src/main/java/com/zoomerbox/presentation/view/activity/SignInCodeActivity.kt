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
import com.zoomerbox.databinding.ActivitySignInCodeBinding
import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.presentation.view.util.BundleKeys
import com.zoomerbox.presentation.viewmodel.SignInViewModel
import com.zoomerbox.presentation.viewmodel.SignInViewModelFactory
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.lang.ref.WeakReference
import javax.inject.Inject

class SignInCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInCodeBinding
    private lateinit var viewModel: SignInViewModel
    private var verificationId: String? = null
    private var phoneNumber: String? = null

    @Inject
    lateinit var viewModelFactory: SignInViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.codeErrorText.visibility = View.GONE
        binding.waveProgressView.visibility = View.GONE

        verificationId = intent.getStringExtra(BundleKeys.VERIFICATION_ID)
        phoneNumber = intent.getStringExtra(BundleKeys.PHONE_NUMBER)

        provideDependencies()
        createViewModel()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.getResultLiveData().observe(this, {
            startActivity(DefaultActivity.newIntent(this))
        })
        viewModel.getProgressLiveData().observe(this, {
            if (it) {
                binding.waveProgressView.visibility = View.VISIBLE
                binding.waveProgressView.translationY = 1000F
                binding.waveProgressView.alpha = 0F
                binding.waveProgressView.startAnimation()
                binding.waveProgressView
                    .animate()
                    .alpha(1.0F)
                    .translationY(0F).interpolator = LinearInterpolator()
            } else {
                binding.waveProgressView.translationY = 0F
                binding.waveProgressView.alpha = 1.0F
                binding.waveProgressView
                    .animate()
                    .alpha(0F)
                    .translationY(1000F).interpolator = LinearInterpolator()
            }
        })
        viewModel.getErrorLiveData().observe(this, {
            Log.d("${TAG}_ERROR", it.message.toString())
            errorText.text = "Провалище! Проверьте введенный код и попробуйте снова."
            errorText.visibility = View.VISIBLE
        })
    }

    private fun setListeners() {
        if (verificationId == null || phoneNumber == null) {
            val intent = SignInActivity.newIntent(this)
            startActivity(intent)
        }
        binding.verifyCodeBtn.setOnClickListener { _ ->
            val verificationCode = "${binding.smsCodeText.text}"
            verificationId?.let { id -> viewModel.verifyWithCode(id, verificationCode) }
        }
        binding.sendOnceMore.setOnClickListener {
            phoneNumber?.let { number -> viewModel.verifyPhoneNumber(number, WeakReference(this)) }
        }
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
            return Intent(context, SignInCodeActivity::class.java)
        }
    }
}