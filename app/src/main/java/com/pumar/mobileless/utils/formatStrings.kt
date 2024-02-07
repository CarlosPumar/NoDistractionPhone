package com.pumar.mobileless.utils

fun stringifyArray(array: Array<String>): String {
    return array.joinToString(separator = ",")
}

fun parseStringToArray(stringifyArray: String): Array<String> {
    if (stringifyArray == "") return emptyArray()
    return ArrayList(stringifyArray.split(",")).toTypedArray()
}
