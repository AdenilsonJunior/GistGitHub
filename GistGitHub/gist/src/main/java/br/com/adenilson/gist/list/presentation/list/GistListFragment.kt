package br.com.adenilson.gist.list.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.paging.CombinedLoadStates
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adenilson.base.androidextensions.isSourceError
import br.com.adenilson.base.androidextensions.isSourceLoading
import br.com.adenilson.base.androidextensions.showLongToast
import br.com.adenilson.base.presentation.BaseFragment
import br.com.adenilson.gist.R
import com.bumptech.glide.load.HttpException
import kotlinx.android.synthetic.main.fragment_gist_list.recyclerViewGist
import kotlinx.android.synthetic.main.fragment_gist_list.swipeRefreshLayout
import javax.inject.Inject

class GistListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: GistListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GistListViewModel::class.java)
    }

    private val adapter = GistListAdapter {
        // Navigate
    }

    private val loadingState: (CombinedLoadStates) -> Unit = { combinedLoadStates ->
        with(combinedLoadStates) {
            swipeRefreshLayout.isRefreshing = isSourceLoading()
            isSourceError(
                refreshError = { error ->
                    handleError(error)
                }
            )
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is HttpException -> requireContext().showLongToast(getString(R.string.gist_connection_message_error))
            else -> requireContext().showLongToast(throwable.localizedMessage.orEmpty())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gist_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeRefreshLayout()
        setupRecyclerView()
        viewModel.loadGist()
        viewModel.pagedList?.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setupRecyclerView() {
        recyclerViewGist.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewGist.addItemDecoration(SpaceItemDecoration(requireContext()))
        recyclerViewGist.adapter = adapter
        adapter.addLoadStateListener(loadingState)
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.primaryDark
            )
        )
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadGist()
        }
    }

    override fun onDestroyView() {
        adapter.removeLoadStateListener(loadingState)
        super.onDestroyView()
    }
}
