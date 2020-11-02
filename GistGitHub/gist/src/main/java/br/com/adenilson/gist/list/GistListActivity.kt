package br.com.adenilson.gist.list

import android.os.Bundle
import br.com.adenilson.base.presentation.BaseActivity
import br.com.adenilson.gist.R
import kotlinx.android.synthetic.main.layout_toolbar.toolbar

class GistListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gist_list)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }
}