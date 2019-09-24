package com.tohami.egyptinnovate.utilities

fun Any?.ifNull(block: () -> Unit) {
    if(this == null)
        block()
}