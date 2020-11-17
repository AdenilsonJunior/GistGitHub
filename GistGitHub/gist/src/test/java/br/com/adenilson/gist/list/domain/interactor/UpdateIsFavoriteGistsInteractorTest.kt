package br.com.adenilson.gist.list.domain.interactor

import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
import br.com.adenilson.gist.commons.data.repository.GistRepository
import br.com.adenilson.gist.commons.domain.model.File
import br.com.adenilson.gist.commons.domain.model.Gist
import br.com.adenilson.gist.commons.domain.model.Owner
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class UpdateIsFavoriteGistsInteractorTest {

    private lateinit var interactor: UpdateIsFavoriteGistsInteractor
    private val repository: GistRepository = mock()

    @Before
    fun setup() {
        interactor = UpdateIsFavoriteGistsInteractorImpl(repository)
    }

    @Test
    fun `should update the favorites gists with success`() {
        val expectedFavoriteSize = 2
        whenever(repository.getFavoriteGists()).thenReturn(Single.just(localFavoriteGists))
        interactor.execute(gists).test().run {
            assertComplete()
            assertNoErrors()

            assertValue { value ->
                value.filter { it.favorite }.size == expectedFavoriteSize
            }

            verify(repository, times(1)).getFavoriteGists()
            verifyNoMoreInteractions(repository)
        }
    }

    @Test
    fun `should not update the favorites gists given repository throws error`() {
        whenever(repository.getFavoriteGists()).thenReturn(Single.error(Exception()))
        interactor.execute(gists).test().run {
            assertNotComplete()
            assertError(Exception::class.java)
            assertNoValues()

            verify(repository, times(1)).getFavoriteGists()
            verifyNoMoreInteractions(repository)
        }
    }

    private val localFavoriteGists = listOf(
        GistEntity(
            files = listOf(Mockito.mock(FileEntity::class.java)),
            owner = Mockito.mock(OwnerEntity::class.java),
            favorite = false,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "favorite1",
            id = 1
        ),
        GistEntity(
            files = listOf(Mockito.mock(FileEntity::class.java)),
            owner = Mockito.mock(OwnerEntity::class.java),
            favorite = false,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "favorite2",
            id = 2
        ),
        GistEntity(
            files = listOf(Mockito.mock(FileEntity::class.java)),
            owner = Mockito.mock(OwnerEntity::class.java),
            favorite = false,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "favorite3",
            id = 3
        )
    )

    private val gists = listOf(
        Gist(
            files = listOf(Mockito.mock(File::class.java)),
            owner = Mockito.mock(Owner::class.java),
            favorite = false,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "favorite1"
        ),
        Gist(
            files = listOf(Mockito.mock(File::class.java)),
            owner = Mockito.mock(Owner::class.java),
            favorite = false,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "webId"
        ),
        Gist(
            files = listOf(Mockito.mock(File::class.java)),
            owner = Mockito.mock(Owner::class.java),
            favorite = false,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "favorite2"
        )
    )

    private val expected = listOf(
        Gist(
            files = listOf(Mockito.mock(File::class.java)),
            owner = Mockito.mock(Owner::class.java),
            favorite = true,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "favorite1"
        ),
        Gist(
            files = listOf(Mockito.mock(File::class.java)),
            owner = Mockito.mock(Owner::class.java),
            favorite = false,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "webId"
        ),
        Gist(
            files = listOf(Mockito.mock(File::class.java)),
            owner = Mockito.mock(Owner::class.java),
            favorite = true,
            lastUpdate = Mockito.mock(Date::class.java),
            description = "",
            webId = "favorite2"
        )
    )
}