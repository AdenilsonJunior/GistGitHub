package br.com.adenilson.gist.list.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adenilson.base.presentation.BaseFragment
import br.com.adenilson.gist.R
import br.com.adenilson.gist.list.domain.model.Gist
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
        setupViewModel()
        viewModel.loadGist()
    }

    private fun setupRecyclerView() {
        recyclerViewGist.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewGist.addItemDecoration(SpaceItemDecoration(requireContext()))
        recyclerViewGist.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel.clearStates()
        viewModel.gistListState.observe(viewLifecycleOwner, this::onGistList)
        viewModel.loadingState.observe(viewLifecycleOwner, this::onLoading)
    }

    private fun onLoading(state: GistListViewModel.LoadingState) {
        when (state) {
            GistListViewModel.LoadingState.Start -> swipeRefreshLayout.isRefreshing = true
            GistListViewModel.LoadingState.Finish -> swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun onGistList(state: GistListViewModel.GistListState) {
        when (state) {
            is GistListViewModel.GistListState.Loaded -> loadGistList(state.model)
            is GistListViewModel.GistListState.Error -> onGistListError(state.throwable)
        }
    }

    private fun onGistListError(throwable: Throwable) {
        Toast.makeText(requireContext(), "Não foi possível carregar a lista", Toast.LENGTH_SHORT)
            .show()
    }

    private fun loadGistList(model: List<Gist>) {
        adapter.data = model.toMutableList()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.primaryDark
            )
        )
        swipeRefreshLayout.setOnRefreshListener {
        }
    }
}