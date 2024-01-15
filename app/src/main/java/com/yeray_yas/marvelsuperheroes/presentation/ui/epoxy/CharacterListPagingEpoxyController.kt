package com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.squareup.picasso.Picasso
import com.yeray_yas.marvelsuperheroes.R
import com.yeray_yas.marvelsuperheroes.data.network.response.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterListItemBinding
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterListTitleBinding
import java.util.Locale

class CharacterListPagingEpoxyController(
    private val onCharacterSelected: (Int) -> Unit
) : PagedListEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        val image = item?.data?.results?.get(0)?.thumbnail?.run {
            "$path.$extension".takeIf { it.isNotBlank() }
        } ?: ""
        return CharacterGridItemEpoxyModel(
            characterId = item!!.data.results[0].id,
            imageUrl = image,
            name = item.data.results[0].name,
            onCharacterSelected = onCharacterSelected).id(
            item.data.results[0].id
        )
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        CharacterGridTitleEpoxyModel("Numbers")
            .id("main_family_header")
            .addTo(this)

        super.addModels(models.subList(0, 1))

        (models.subList(1, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.name[0].uppercaseChar()
        }.forEach { mapEntry ->
            val character = mapEntry.key.toString().uppercase(Locale.US)
            CharacterGridTitleEpoxyModel(title = character)
                .id(character)
                .addTo(this)

            super.addModels(mapEntry.value)
        }
    }


    // Data classes
    data class CharacterGridItemEpoxyModel(
        val characterId: Int,
        val imageUrl: String,
        val name: String,
        private val onCharacterSelected: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name

            root.setOnClickListener {
                onCharacterSelected(characterId)
            }
        }
    }

    data class CharacterGridTitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {

        override fun ModelCharacterListTitleBinding.bind() {
            textView.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}