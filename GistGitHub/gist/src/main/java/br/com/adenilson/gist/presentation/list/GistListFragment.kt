package br.com.adenilson.gist.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adenilson.base.androidextensions.isSourceError
import br.com.adenilson.base.androidextensions.isSourceLoading
import br.com.adenilson.base.androidextensions.showLongToast
import br.com.adenilson.base.presentation.BaseFragment
import br.com.adenilson.gist.R
import br.com.adenilson.gist.presentation.list.adapter.GistListAdapter
import br.com.adenilson.gist.presentation.list.adapter.ListSpaceItemDecoration
import br.com.adenilson.gist.presentation.model.Gist
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

    private val adapter by lazy {
        GistListAdapter(
            listener = {
                findNavController().navigate(
                    GistListFragmentDirections.gistListFragmentToGistDetailsFragment(
                        it
                    )
                )
            },
            favoriteClickListener = {
                viewModel.favoriteClick(it)
            }
        )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuFavorites -> findNavController().navigate(GistListFragmentDirections.gistListFragmentToFavoriteGistsFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupSwipeRefreshLayout()
        setupRecyclerView()
        viewModel.loadGist()
        viewModel.pagedList?.cachedIn(viewLifecycleOwner.lifecycle)?.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.favoriteGistState.observe(viewLifecycleOwner, this::onFavorite)
    }

    private fun onFavorite(state: GistListViewModel.FavoriteGistState) {
        when(state) {
            is GistListViewModel.FavoriteGistState.Success -> updateFavoriteGist(state.gist)
            is GistListViewModel.FavoriteGistState.Error -> showError()
        }
    }

    private fun showError() {
        requireContext().showLongToast("Não foi possível favoritar esse gist.")
    }

    private fun updateFavoriteGist(gist: Gist) {
        adapter.updateFavoriteGist(gist)
    }

    private fun onFavoriteError() {
        requireContext().showLongToast("Error")
    }

    private fun onFavoriteSuccess() {
        requireContext().showLongToast("Successo")
    }



    private fun setupRecyclerView() {
        recyclerViewGist.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewGist.addItemDecoration(ListSpaceItemDecoration(requireContext()))
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
            adapter.refresh()
        }
    }

    override fun onDestroyView() {
        adapter.removeLoadStateListener(loadingState)
        super.onDestroyView()
    }
}
