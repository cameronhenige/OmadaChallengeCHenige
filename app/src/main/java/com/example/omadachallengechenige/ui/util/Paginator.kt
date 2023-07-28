package com.example.omadachallengechenige.ui.util

interface Paginator<Key, Item> {
    suspend fun loadNextItems(forceFetch: Boolean)
    fun reset()
}