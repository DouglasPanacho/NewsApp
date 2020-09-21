package com.doug.newsapp.helpers

sealed class ViewStatus<T> {
    class ErrorStatus<T> : ViewStatus<T>()
    class LoadingStatus<T> : ViewStatus<T>()
    data class SuccessStatus<T>(var items: T) : ViewStatus<T>()
}