package com.hcc.readerapp.util


val Float.dp
    get() = DestinyUtils.fromDp(this)

val Float.sp
    get() = DestinyUtils.fromSp(this)

val Int.dp
    get() = DestinyUtils.fromDp(this.toFloat()).toInt()

val Int.sp
    get() = DestinyUtils.fromSp(this.toFloat()).toInt()