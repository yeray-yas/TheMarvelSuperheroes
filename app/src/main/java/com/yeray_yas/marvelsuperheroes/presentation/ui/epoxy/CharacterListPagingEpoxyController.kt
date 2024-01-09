package com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.squareup.picasso.Picasso
import com.yeray_yas.marvelsuperheroes.R
import com.yeray_yas.marvelsuperheroes.data.model.CharacterData
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterListItemBinding

class CharacterListPagingEpoxyController(
    //private val onCharacterSelected: (Int) -> Unit
): PagedListEpoxyController<CharacterData>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: CharacterData?
    ): EpoxyModel<*> {
        val image = item?.run { "$thumbnail.path.$thumbnail.extension" } ?: ""
        return CharacterGridItemEpoxyModel(image, item?.name.orEmpty()).id(item!!.id)
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