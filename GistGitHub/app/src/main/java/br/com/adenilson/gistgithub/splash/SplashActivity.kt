package br.com.adenilson.gistgithub.splash

import android.os.Bundle
import br.com.adenilson.base.activity.BaseActivity
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.gistgithub.R
import kotlinx.android.synthetic.main.activity_splash.button
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        button.setOnClickListener {
            navigator.navigateToGistListActivity(this)
        }
    }
}