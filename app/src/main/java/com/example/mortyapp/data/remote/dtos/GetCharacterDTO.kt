package com.example.mortyapp.data.remote.dtos

/**
 *
{
"id": 2,
"name": "Morty Smith",
"status": "Alive",
"species": "Human",
"type": "",
"gender": "Male",
"origin": {
"name": "Earth (C-137)",
"url": "https://rickandmortyapi.com/api/location/1"
},
"location": {
"name": "Earth (Replacement Dimension)",
"url": "https://rickandmortyapi.com/api/location/20"
},
"image": "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
"episode": [
"https://rickandmortyapi.com/api/episode/1",
"https://rickandmortyapi.com/api/episode/2",
"https://rickandmortyapi.com/api/episode/3"
],
"url": "https://rickandmortyapi.com/api/character/2",
"created": "2017-11-04T18:50:21.651Z"
}
 * */
data class GetCharacterDTO(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    data class Location(
        val name: String,
        val url: String
    )

    data class Origin(
        val name: String,
        val url: String
    )
}