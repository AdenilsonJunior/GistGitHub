package br.com.adenilson.gist.list

import android.os.Bundle
import br.com.adenilson.base.activity.BaseActivity
import br.com.adenilson.gist.R

class GistListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gist_list)
    }
}