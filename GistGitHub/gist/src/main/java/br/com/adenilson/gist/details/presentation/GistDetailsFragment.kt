package br.com.adenilson.gist.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adenilson.base.androidextensions.createViewModel
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.base.presentation.BaseFragment
import br.com.adenilson.gist.R
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.details.presentation.adapter.DetailsSpaceItemDecoration
import br.com.adenilson.gist.details.presentation.adapter.GistDetailsAdapter
import br.com.adenilson.gist.details.presentation.adapter.GistDetailsItem
import br.com.adenilson.gist.details.presentation.adapter.NavigableUrlItem
import kotlinx.android.synthetic.main.fragment_gist_details.recyclerViewDetails
import javax.inject.Inject

open class GistDetailsFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.createViewModel<GistDetailsViewModel>(this)
    }

    private val args: GistDetailsFragmentArgs by navArgs()
    private val gist: Gist
        get() = args.gist

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gist_details, container, false)
    }

    private val adapter by lazy {
        GistDetailsAdapter {
            when (it) {
                is NavigableUrlItem -> navigator.navigateToBrowser(requireContext(), it.url)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.prepareGistDetails(gist)
        viewModel.gistDetailsState.observe(viewLifecycleOwner, this::onPrepareGist)
    }

    private fun setupRecyclerView() {
        recyclerViewDetails.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewDetails.adapter = adapter
        recyclerViewDetails.addItemDecoration(DetailsSpaceItemDecoration(requireContext()))
    }

    private fun onPrepareGist(state: GistDetailsViewModel.GistDetailsState) {
        when (state) {
            is GistDetailsViewModel.GistDetailsState.Prepared -> bindDetails(state.list)
        }
    }

    private fun bindDetails(list: List<GistDetailsItem>) {
        adapter.data = list
    }
}