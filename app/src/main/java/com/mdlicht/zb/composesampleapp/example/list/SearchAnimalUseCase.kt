package com.mdlicht.zb.composesampleapp.example.list

class SearchAnimalUseCase {
    private val _repository: ListExampleRepository = ListExampleRepositoryImpl()

    operator fun invoke(animalName: String) : List<String> {
        return _repository.searchAnimal(animalName = animalName)
    }
}