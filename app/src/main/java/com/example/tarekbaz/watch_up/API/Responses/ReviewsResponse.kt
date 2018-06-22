package com.example.tarekbaz.watch_up.API.Responses

import com.example.tarekbaz.watch_up.Models.Comment

data class ReviewsResponse(
        var results : List<Comment>
) {
}