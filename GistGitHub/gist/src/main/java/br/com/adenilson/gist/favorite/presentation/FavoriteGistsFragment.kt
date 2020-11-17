package br.com.adenilson.gist.favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adenilson.base.androidextensions.createViewModel
import br.com.adenilson.base.androidextensions.hide
import br.com.adenilson.base.androidextensions.show
import br.com.adenilson.base.androidextensions.showSnackBar
import br.com.adenilson.base.presentation.BaseFragment
import br.com.adenilson.gist.R
import br.com.adenilson.gist.favorite.presentation.adapter.FavoriteGistsAdapter
import br.com.adenilson.gist.list.presentation.adapter.ListSpaceItemDecoration
import br.com.adenilson.gist.commons.domain.model.Gist
import kotlinx.android.synthetic.main.fragment_favorite_gists.layoutEmpty
import kotlinx.android.synthetic.main.fragment_favorite_gists.layoutLoading
import kotlinx.android.synthetic.main.fragment_favorite_gists.recyclerViewFavoriteGists
import javax.inject.Inject

class FavoriteGistsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.createViewModel<FavoriteGistsViewModel>(this)
    }

    private val adapter: FavoriteGistsAdapter by lazy {
        FavoriteGistsAdapter(
            viewTypesListener = {
                findNavController().navigate(
                    FavoriteGistsFragmentDirections.favoriteGistsFragmentToGistDetailsFragment(it)
                )
            },
            favoriteClickListener = {
                viewModel.favoriteGist(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_gists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        recyclerViewFavoriteGists.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewFavoriteGists.addItemDecoration(ListSpaceItemDecoration(requireContext()))
        recyclerViewFavoriteGists.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel.favoriteGistsState.observe(viewLifecycleOwner, this::onFavoriteGists)
        viewModel.loadingState.observe(viewLifecycleOwner, this::onLoading)
        viewModel.loadFavorites()
    }

    private fun onLoading(state: FavoriteGistsViewModel.LoadingState) {
        when (state) {
            FavoriteGistsViewModel.LoadingState.Start -> showLoading()
            FavoriteGistsViewModel.LoadingState.End -> hideLoading()
        }
    }

    private fun hideLoading() {
        layoutLoading.hide()
    }

    private fun showLoading() {
        layoutLoading.show()
    }

    private fun onFavoriteGists(state: FavoriteGistsViewModel.FavoriteGistsState) {
        when (state) {
            is FavoriteGistsViewModel.FavoriteGistsState.Loaded -> loadFavoriteGists(state.gists)
            is FavoriteGistsViewModel.FavoriteGistsState.Error -> showError()
            is FavoriteGistsViewModel.FavoriteGistsState.Favorite -> updateFavoriteGist(state.gist)
            is FavoriteGistsViewModel.FavoriteGistsState.UnFavorite -> updateFavoriteGist(state.gist)
            FavoriteGistsViewModel.FavoriteGistsState.Empty -> showEmpty()
        }
    }

    private fun showEmpty() {
        layoutEmpty.show()
    }

    private fun updateFavoriteGist(gist: Gist) {
        adapter.updateFavoriteGist(gist)
    }

    private fun showError() {
        showSnackBar(getString(R.string.gist_favorite_error_load))
    }

    private fun loadFavoriteGists(gists: List<Gist>) {
        layoutEmpty.hide()
        adapter.data = gists
    }
}