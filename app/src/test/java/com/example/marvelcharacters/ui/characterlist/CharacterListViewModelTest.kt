package com.example.marvelcharacters.ui.characterlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvelcharacters.MainDispatcherRule
import com.example.marvelcharacters.core.utils.AppError
import com.example.marvelcharacters.core.utils.Resource
import com.example.marvelcharacters.core.utils.UIMessage
import com.example.marvelcharacters.domain.model.ImageUrl
import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.domain.model.PaginatedData
import com.example.marvelcharacters.domain.repository.CharacterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CharacterListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Needed for LiveData/StateFlow testing

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: CharacterRepository
    private lateinit var viewModel: CharacterListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this) // Initialize @Mock annotated fields
        viewModel = CharacterListViewModel(repository)
    }

    @Test
    fun `when getNextPage is called, characterList should be updated`() = runTest {
        //ARRANGE
        val characters = listOf(
            MarvelCharacter(
                id = 1,
                name = "ABC",
                description = "",
                imgUrl = ImageUrl(),
                comics = null,
                stories = null,
                series = null,
                events = null
            ),
            MarvelCharacter(
                id = 2,
                name = "XYZ",
                description = "",
                imgUrl = ImageUrl(),
                comics = null,
                stories = null,
                series = null,
                events = null
            )
        )

        val mockedData: Resource<PaginatedData<MarvelCharacter>> =
            Resource.Success(PaginatedData(characters, null, null, null))

        `when`(
            repository.getCharactersList(viewModel.currentOffset, viewModel.pageSize)
        ).thenReturn(mockedData)

        //ACT
        viewModel.getNextPage()
        val viewModelCharactersList = viewModel.itemList.first()

        //ASSERT
        assertThat(viewModelCharactersList).containsExactlyElementsIn(characters).inOrder()
    }

    @Test
    fun `when getNextPage is called, characterList size should increment with the returned list size`() =
        //ARRANGE
        runTest {
            val characters = listOf(
                MarvelCharacter(
                    id = 1,
                    name = "ABC",
                    description = "",
                    imgUrl = ImageUrl(),
                    comics = null,
                    stories = null,
                    series = null,
                    events = null
                ),
                MarvelCharacter(
                    id = 2,
                    name = "XYZ",
                    description = "",
                    imgUrl = ImageUrl(),
                    comics = null,
                    stories = null,
                    series = null,
                    events = null
                )
            )

            val mockedData: Resource<PaginatedData<MarvelCharacter>> =
                Resource.Success(PaginatedData(characters, null, null, null))

            `when`(
                repository.getCharactersList(viewModel.currentOffset, viewModel.pageSize)
            ).thenReturn(mockedData)

            //ACT
            val oldListSize = viewModel.itemList.value?.size ?: 0
            viewModel.getNextPage()
            val newListSize = viewModel.itemList.first()?.size ?: 0

            //ASSERT
            assertThat(newListSize).isEqualTo(oldListSize + characters.size)
        }

    @Test
    fun `when getNextPage returns an error, itemList should not change`() =
        runTest {
            //ARRANGE
            val mockedData: Resource<PaginatedData<MarvelCharacter>> =
                Resource.Error(AppError.JustMessage("Network Error"))

            `when`(
                repository.getCharactersList(viewModel.currentOffset, viewModel.pageSize)
            ).thenReturn(mockedData)


            //ACT
            val oldListSize = viewModel.itemList.value?.size ?: 0
            viewModel.getNextPage()
            val newListSize = viewModel.itemList.first()?.size ?: 0

            //ASSERT
            assertThat(newListSize).isEqualTo(oldListSize)
        }

    @Test
    fun `when getNextPage returns an error, uiMessageState should emit an error UIMessage`() =
        runTest {
            //ARRANGE
            val mockedData: Resource<PaginatedData<MarvelCharacter>> =
                Resource.Error(AppError.JustMessage("Network Error"))

            `when`(
                repository.getCharactersList(viewModel.currentOffset, viewModel.pageSize)
            ).thenReturn(mockedData)

            //ACT (Observing here should be done before calling getNextPage)
            launch {
                val errorMessage = viewModel.uiMessageState.first()
                assertThat(errorMessage).isInstanceOf(UIMessage.Error::class.java)
            }

            //ASSERT
            launch {
                delay(1000)
                viewModel.getNextPage()
            }

        }
}