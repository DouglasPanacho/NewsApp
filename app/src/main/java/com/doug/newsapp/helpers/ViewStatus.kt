package com.doug.newsapp.helpers

sealed class ViewStatus {
    object ErrorStatus : ViewStatus()
    object LoadingStatus : ViewStatus()
    data class SuccessStatus<T>(var items: T) : ViewStatus()
}