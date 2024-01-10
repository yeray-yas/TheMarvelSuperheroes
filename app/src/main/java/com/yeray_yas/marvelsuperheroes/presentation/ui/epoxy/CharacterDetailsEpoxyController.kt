package com.yeray_yas.marvelsuperheroes.presentation.ui.epoxy

import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import com.yeray_yas.marvelsuperheroes.R
import com.yeray_yas.marvelsuperheroes.data.model.GetCharacterByIdResponse
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterDetailsDataPointBinding
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterDetailsHeaderBinding
import com.yeray_yas.marvelsuperheroes.databinding.ModelCharacterDetailsImageBinding

class CharacterDetailsEpoxyController : EpoxyController() {

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
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if (characterResponse == null) {
            // TODO: Manejar el estado de error adecuadamente
            return
        }

        val characterResult = characterResponse!!.data.results.getOrNull(0)

        val name = characterResult?.name.orEmpty()
        val image = characterResult?.thumbnail?.run {
            "$path.$extension"
        } ?: ""
        val description = characterResult?.description.orEmpty()

        HeaderEpoxyModel(
            name = name
        ).id("header").addTo(this)

        ImageEpoxyModel(
            imageUrl = image
        ).id("image").addTo(this)

        DataPointEpoxyModel(
            title = "Description",
            description = description
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