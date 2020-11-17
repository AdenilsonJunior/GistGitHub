package br.com.adenilson.gist.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.adenilson.base.presentation.viewmodel.BaseViewModel
import br.com.adenilson.gist.details.presentation.adapter.FileItem
import br.com.adenilson.gist.details.presentation.adapter.FilesHeaderItem
import br.com.adenilson.gist.details.presentation.adapter.GistDetailsItem
import br.com.adenilson.gist.details.presentation.adapter.HeaderItem
import br.com.adenilson.gist.details.presentation.adapter.UpdateDateItem
import br.com.adenilson.gist.common.domain.model.File
import br.com.adenilson.gist.common.domain.model.Gist
import java.util.Date
import javax.inject.Inject

class GistDetailsViewModel @Inject constructor() : BaseViewModel() {

    private val _gistDetailsState: MutableLiveData<GistDetailsState> = MutableLiveData()
    val gistDetailsState: LiveData<GistDetailsState> = _gistDetailsState

    fun prepareGistDetails(gist: Gist) {
        mutableListOf<GistDetailsItem>().apply {
            addHeaderItem(gist)
            addFileHeader(gist.files.size)
            addFilesItem(gist.files)
            addDateItem(gist.lastUpdate)
        }.also { detailsItems ->
            _gistDetailsState.postValue(GistDetailsState.Prepared(detailsItems))
        }
    }

    private fun MutableList<GistDetailsItem>.addDateItem(lastUpdate: Date?) {
        lastUpdate?.let { date ->
            add(UpdateDateItem(date))
        }
    }

    private fun MutableList<GistDetailsItem>.addFileHeader(size: Int) {
        add(FilesHeaderItem(size))
    }

    private fun MutableList<GistDetailsItem>.addFilesItem(files: List<File>) {
        addAll(
            files.map { file ->
                FileItem(
                    filename = file.filename,
                    type = file.type,
                    rawUrl = file.rawUrl,
                    language = file.language,
                    size = file.size
                )
            }
        )
    }

    private fun MutableList<GistDetailsItem>.addHeaderItem(gist: Gist) {
        add(
            HeaderItem(
                userName = gist.owner.name,
                avatar = gist.owner.avatarUrl,
                description = gist.description,
                gistType = gist.gistType
            )
        )
    }

    sealed class GistDetailsState {
        data class Prepared(val list: List<GistDetailsItem>) : GistDetailsState()
    }
}