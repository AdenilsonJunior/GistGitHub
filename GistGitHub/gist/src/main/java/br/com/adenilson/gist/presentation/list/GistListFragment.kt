package br.com.adenilson.gist.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adenilson.base.androidextensions.checkSourceLoadState
import br.com.adenilson.base.androidextensions.hide
import br.com.adenilson.base.androidextensions.show
import br.com.adenilson.base.androidextensions.showIndefiniteSnackBar
import br.com.adenilson.base.androidextensions.showSnackBar
import br.com.adenilson.base.presentation.BaseFragment
import br.com.adenilson.gist.R
import br.com.adenilson.gist.presentation.list.adapter.GistListAdapter
import br.com.adenilson.gist.presentation.list.adapter.ListSpaceItemDecoration
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_gist_list.editTextSearch
import kotlinx.android.synthetic.main.fragment_gist_list.layoutError
import kotlinx.android.synthetic.main.fragment_gist_list.recyclerViewGist
import kotlinx.android.synthetic.main.fragment_gist_list.swipeRefreshLayout
import java.io.IOException
import java.util.concurrent.TimeUnit
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
            checkSourceLoadState(
                notLoading = {
                    layoutError.hide()
                    swipeRefreshLayout.isRefreshing = false
                },
                appendLoading = {
                    layoutError.hide()
                    swipeRefreshLayout.isRefreshing = true
                },
                refreshLoading = {
                    layoutError.hide()
                    swipeRefreshLayout.isRefreshing = true
                },
                refreshError = { error ->
                    swipeRefreshLayout.isRefreshing = false
//                    layoutError.show()
                    handleError(error)
                },
                appendError = { error ->
                    swipeRefreshLayout.isRefreshing = false
                    handleError(error)
                }
            )
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is IOException -> showIndefiniteSnackBar(
                getString(R.string.gist_connection_message_error),
                getString(R.string.gist_button_text_try_again)
            ) {
                adapter.retry()
            }
            else -> showSnackBar(getString(R.string.gist_load_list_error))
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
        when (item.itemId) {
            R.id.menuFavorites -> findNavController().navigate(GistListFragmentDirections.gistListFragmentToFavoriteGistsFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupSwipeRefreshLayout()
        setupRecyclerView()
        setupSearchEditText()
        viewModel.pagedList?.cachedIn(viewLifecycleOwner.lifecycle)?.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.favoriteGistState.observe(viewLifecycleOwner, this::onFavorite)
    }

    private fun setupSearchEditText() {
        editTextSearch.afterTextChangeEvents().skipInitialValue().debounce(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                viewModel.loadGist(it.editable?.toString().orEmpty())
            }
    }

    private fun onFavorite(state: GistListViewModel.FavoriteGistState) {
        when (state) {
            is GistListViewModel.FavoriteGistState.Success -> updateFavoriteGist()
            is GistListViewModel.FavoriteGistState.Error -> onFavoriteError()
        }
    }

    private fun updateFavoriteGist() {
        adapter.notifyDataSetChanged()
    }

    private fun onFavoriteError() {
        showSnackBar(getString(R.string.gist_favorite_error))
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
