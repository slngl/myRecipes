package com.slngl.myrecipes.network.model

data class DataHolder<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): DataHolder<T> {
            return DataHolder(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): DataHolder<T> {
            return DataHolder(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): DataHolder<T> {
            return DataHolder(Status.LOADING, data, null)
        }

    }

}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}