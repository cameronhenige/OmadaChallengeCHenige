package com.example.omadachallengechenige.ui.util

class FlickrPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> List<Item>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey: Key = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems(forceFetch: Boolean) {
        if (!forceFetch && isMakingRequest) {
            return
        }

        isMakingRequest = true
        onLoadUpdated(true)
        try {
            val result = onRequest(currentKey)
            isMakingRequest = false
            currentKey = getNextKey(result)
            onSuccess(result, currentKey)
            onLoadUpdated(false)
        } catch (exception: Exception) {
            onError(exception)
            onLoadUpdated(false)
        }
    }

    override fun reset() {
        currentKey = initialKey
    }


}
