package br.com.adenilson.splash.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import br.com.adenilson.base.androidextensions.createViewModel
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.base.presentation.BaseActivity
import br.com.adenilson.splash.R
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.createViewModel<SplashViewModel>(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupViewModel()
        viewModel.load()
    }

    private fun setupViewModel() {
        viewModel.splashState.observe(this, this::onLoad)
    }

    private fun onLoad(state: SplashViewModel.SplashState) {
        when (state) {
            SplashViewModel.SplashState.Finish -> navigateToGistList()
        }
    }

    private fun navigateToGistList() {
        navigator.navigateToGistListActivity(this)
        finish()
    }
}