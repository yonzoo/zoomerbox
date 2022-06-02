package com.zoomerbox.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.R
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.presentation.viewmodel.DefaultViewModel
import com.zoomerbox.presentation.viewmodel.DefaultViewModelFactory
import javax.inject.Inject

/**
 * Базовая активити, в которой происходит аутентификация пользователя и редирект
 * на экран авторизации или экран с товарами
 */
class DefaultActivity : AppCompatActivity() {

    private lateinit var viewModel: DefaultViewModel

    @Inject
    lateinit var viewModelFactory: DefaultViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        provideDependencies()
        createViewModel()
        setObservers()

        viewModel.authenticate()
    }

    private fun setObservers() {
        viewModel.getErrorLiveData().observe(this) {
            startActivity(SignInActivity.newIntent(this))
        }
        viewModel.getUserLiveData().observe(this) {
            startActivity(MainActivity.newIntent(this))
        }
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(DefaultViewModel::class.java)
    }

    private fun provideDependencies() {
        val activityComponent: ActivityComponent =
            ZoomerboxApplication.getAppComponent(this).getActivityComponent()
        activityComponent.inject(this)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, DefaultActivity::class.java)
        }
    }
}