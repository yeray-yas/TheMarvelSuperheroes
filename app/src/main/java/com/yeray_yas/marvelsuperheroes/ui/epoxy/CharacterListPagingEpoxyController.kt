package com.yeray_yas.marvelsuperheroes.ui.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.squareup.picasso.Picasso
import com.yeray_yas.marvelsuperheroes.R
import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterListItemBinding

class CharacterListPagingEpoxyController(
    //private val onCharacterSelected: (Int) -> Unit
): PagedListEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        val image = item?.data?.results?.get(0)?.thumbnail?.run {
            "$path.$extension".takeIf { it.isNotBlank() }
        } ?: ""
        return CharacterGridItemEpoxyModel(image, item?.data?.results?.get(0)?.name.orEmpty()).id(item!!.data.results[0].id)
    }

    data class CharacterGridItemEpoxyModel(
        val imageUrl: String,
        val name: String
    ): ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
        }
    }
}