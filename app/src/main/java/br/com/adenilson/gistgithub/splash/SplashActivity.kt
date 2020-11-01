package br.com.adenilson.gistgithub.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.gistgithub.R
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_splash.button

class SplashActivity : AppCompatActivity() {

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