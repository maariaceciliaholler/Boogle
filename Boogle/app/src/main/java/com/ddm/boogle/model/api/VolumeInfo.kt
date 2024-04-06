package com.ddm.boogle.model.api

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val description: String?,
    val publishedDate: String?,
    val infoLink: String
)
