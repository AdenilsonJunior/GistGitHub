package br.com.adenilson.gist.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adenilson.base.androidextensions.showLongToast
import br.com.adenilson.base.presentation.BaseFragment
import br.com.adenilson.gist.R
import br.com.adenilson.gist.presentation.favorite.adapter.FavoriteGistsAdapter
import br.com.adenilson.gist.presentation.list.adapter.ListSpaceItemDecoration
import br.com.adenilson.gist.presentation.model.Gist
import kotlinx.android.synthetic.main.fragment_favorite_gists.recyclerViewFavoriteGists
import javax.inject.Inject

class FavoriteGistsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FavoriteGistsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(FavoriteGistsViewModel::class.java)
    }

    private val adapter: FavoriteGistsAdapter by lazy {
        FavoriteGistsAdapter(
            viewTypesListener = {
                findNavController().navigate(FavoriteGistsFragmentDirections.favoriteGistsFragmentToGistDetailsFragment(it))

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
        viewModel.loadFavorites()
    }

    private fun onFavoriteGists(state: FavoriteGistsViewModel.FavoriteGistsState) {
        when (state) {
            is FavoriteGistsViewModel.FavoriteGistsState.Loaded -> loadFavoriteGists(state.gists)
            is FavoriteGistsViewModel.FavoriteGistsState.Error -> showError()
            is FavoriteGistsViewModel.FavoriteGistsState.Favorite -> updateFavoriteGist(state.gist)
            is FavoriteGistsViewModel.FavoriteGistsState.UnFavorite -> updateFavoriteGist(state.gist)
        }
    }

    private fun updateFavoriteGist(gist: Gist) {
        adapter.updateFavoriteGist(gist)
    }

    private fun showError() {
        requireContext().showLongToast("Não foi possível carregar os favoritos.")
    }

    private fun loadFavoriteGists(gists: List<Gist>) {
        adapter.data = gists
    }
}