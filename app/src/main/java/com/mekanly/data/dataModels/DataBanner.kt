package com.mekanly.data.dataModels


data class DataBanner(
    val title: String,
    val type: String,
    val description: String,
    val logo: String,
    val image:String
){
    fun parsedImages(): Array<String> {
        return try {
            val cleanString = image.replace("\\", "")
                .removeSurrounding("[", "]")
                .replace("\"", "")
            return cleanString.split(",")
                .map { it.trim() }
                .toTypedArray()
        } catch (e: Exception) {
            emptyArray()
        }
    }

}