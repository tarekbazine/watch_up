package com.example.tarekbaz.watch_up.API.Responses

data class ListPaginatedResponse<T>(
        var results : List<T>
) {
}