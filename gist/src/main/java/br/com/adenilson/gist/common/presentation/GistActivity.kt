package br.com.adenilson.gist.common.presentation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import br.com.adenilson.base.androidextensions.setupToolbarWithNavigationController
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.base.presentation.BaseActivity
import br.com.adenilson.gist.R
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import javax.inject.Inject

class GistActivity : BaseActivity() {

    @Inject
    lateinit var navigator: Navigator

    private val navController: NavController by lazy { findNavController(R.id.navFragmentHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gist_list)
        setupToolbarWithNavigationController(toolbar, navController)
    }
}