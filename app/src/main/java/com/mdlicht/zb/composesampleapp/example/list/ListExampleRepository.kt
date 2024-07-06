package com.mdlicht.zb.composesampleapp.example.list

import androidx.compose.ui.text.toLowerCase
import animalNames

interface ListExampleRepository {
    fun searchAnimal(animalName: String): List<String>
}

class ListExampleRepositoryImpl() : ListExampleRepository {
    override fun searchAnimal(animalName: String): List<String> {
        return animalNames.filter {
            it.lowercase().contains(animalName.lowercase())
        }
    }
}