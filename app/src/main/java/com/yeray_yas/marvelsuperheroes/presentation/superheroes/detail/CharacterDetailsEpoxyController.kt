package com.yeray_yas.marvelsuperheroes.presentation.superheroes.detail

import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import com.yeray_yas.marvelsuperheroes.R
import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterDetailsDataPointBinding
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterDetailsHeaderBinding
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterDetailsImageBinding
import com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy.LoadingEpoxyModel
import com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy.ViewBindingKotlinModel

class CharacterDetailsEpoxyController: EpoxyController() {

    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var characterResponse: GetCharacterByIdResponse? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }
    override fun buildModels() {
        if (isLoading){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if (characterResponse == null){
            // todo error state
            return
        }

        HeaderEpoxyModel(
            name = characterResponse?.data?.results?.getOrNull(0)?.name.orEmpty()
        ).id("header").addTo(this)
        ImageEpoxyModel(
            imageUrl = characterResponse?.data?.results?.get(0)?.thumbnail?.run {
                "$path.$extension"
            } ?: "" ).id("image").addTo(this)
        // Data point models
        DataPointEpoxyModel(
            title = "Description",
            description = characterResponse?.data?.results?.getOrNull(0)?.description.orEmpty()
        ).id("data_point_1").addTo(this)

    }

    data class HeaderEpoxyModel(
        val name: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {

        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
        }
    }

    data class ImageEpoxyModel(
        val imageUrl: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {

        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(headerImageView)
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {

        override fun ModelCharacterDetailsDataPointBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }
}