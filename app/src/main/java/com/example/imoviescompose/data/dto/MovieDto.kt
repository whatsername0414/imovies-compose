package com.example.imoviescompose.data.dto


import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("artistName")
    val artistName: String?,
    @SerializedName("artworkUrl100")
    val artworkUrl100: String?,
    @SerializedName("artworkUrl30")
    val artworkUrl30: String?,
    @SerializedName("artworkUrl60")
    val artworkUrl60: String?,
    @SerializedName("collectionArtistId")
    val collectionArtistId: Int?,
    @SerializedName("collectionArtistViewUrl")
    val collectionArtistViewUrl: String?,
    @SerializedName("collectionCensoredName")
    val collectionCensoredName: String?,
    @SerializedName("collectionExplicitness")
    val collectionExplicitness: String?,
    @SerializedName("collectionHdPrice")
    val collectionHdPrice: Double?,
    @SerializedName("collectionId")
    val collectionId: Int?,
    @SerializedName("collectionName")
    val collectionName: String?,
    @SerializedName("collectionPrice")
    val collectionPrice: Double?,
    @SerializedName("collectionViewUrl")
    val collectionViewUrl: String?,
    @SerializedName("contentAdvisoryRating")
    val contentAdvisoryRating: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("hasITunesExtras")
    val hasITunesExtras: Boolean?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("longDescription")
    val longDescription: String?,
    @SerializedName("previewUrl")
    val previewUrl: String?,
    @SerializedName("primaryGenreName")
    val primaryGenreName: String?,
    @SerializedName("releaseDate")
    val releaseDate: String?,
    @SerializedName("shortDescription")
    val shortDescription: String?,
    @SerializedName("trackCensoredName")
    val trackCensoredName: String?,
    @SerializedName("trackCount")
    val trackCount: Int?,
    @SerializedName("trackExplicitness")
    val trackExplicitness: String?,
    @SerializedName("trackHdPrice")
    val trackHdPrice: Double?,
    @SerializedName("trackHdRentalPrice")
    val trackHdRentalPrice: Double?,
    @SerializedName("trackId")
    val trackId: Int,
    @SerializedName("trackName")
    val trackName: String?,
    @SerializedName("trackNumber")
    val trackNumber: Int?,
    @SerializedName("trackPrice")
    val trackPrice: Double?,
    @SerializedName("trackRentalPrice")
    val trackRentalPrice: Double?,
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Int?,
    @SerializedName("trackViewUrl")
    val trackViewUrl: String?,
    @SerializedName("wrapperType")
    val wrapperType: String?
)