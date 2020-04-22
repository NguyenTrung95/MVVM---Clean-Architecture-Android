package com.nencer.thesieure.ext


sealed class BooleanExt<out T>

class Success<T>(val data: T) : BooleanExt<T>()

object OtherWise : BooleanExt<Nothing>()

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> =
    when {
        this -> {
            Success(block())
        }
        else -> {
            OtherWise
        }
    }

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    when (this) {
        is Success -> this.data
        OtherWise -> block()
    }
