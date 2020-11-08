package br.com.adenilson.gist.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.base.presentation.BaseFragment
import br.com.adenilson.gist.R
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.gist.presentation.details.adapter.DetailsSpaceItemDecoration
import br.com.adenilson.gist.presentation.details.adapter.FileItem
import br.com.adenilson.gist.presentation.details.adapter.GistDetailsAdapter
import br.com.adenilson.gist.presentation.details.adapter.GistDetailsItem
import br.com.adenilson.gist.presentation.model.Gist
import kotlinx.android.synthetic.main.fragment_gist_details.recyclerViewDetails
import javax.inject.Inject

class GistDetailsFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: GistDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GistDetailsViewModel::class.java)
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
                is FileItem -> navigator.navigateToBrowser(requireContext(), it.rawUrl)
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
            GistDetailsViewModel.GistDetailsState.Loading -> TODO()
        }
    }

    private fun bindDetails(list: List<GistDetailsItem>) {
        adapter.data = list
    }
}