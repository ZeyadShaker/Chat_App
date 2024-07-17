package com.chatapp.model

import com.chatapp.R

data class Category(
    val id: String? = null,
    val name: String? = null,
    val image: Int? = null
) {
    companion object {
    val MOVIES = "MOVIES"
        val SPORTS = "SPORTS"
        val MUSIC = "MUSIC"

        fun getCategoriesList(): List<Category> {
            return listOf(
               getCategoryById(MOVIES),
                getCategoryById(SPORTS),
                getCategoryById(MUSIC)
            )
        }
            fun getCategoryById(id: String): Category {
                 return when (id) {
                     MOVIES -> Category("MOVIES", "Movies", R.drawable.movies)
                         SPORTS -> Category("SPORTS", "sports", R.drawable.sports)
                         MUSIC -> Category("MUSIC", "Music", R.drawable.music)
                         else -> Category("MOVIES", "Movies", R.drawable.movies)

                }

            }
    }
}