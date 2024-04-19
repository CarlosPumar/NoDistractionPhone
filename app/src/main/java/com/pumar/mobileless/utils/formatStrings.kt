package com.pumar.mobileless.utils

fun stringifyArray(array: Array<String>): String {
    return array.joinToString(separator = ",")
}

fun stringifyArrayInt(array: Array<Int>): String {
    return array.joinToString(separator = ",")
}

fun parseStringToArray(stringifyArray: String): Array<String> {
    if (stringifyArray == "") return emptyArray()
    return ArrayList(stringifyArray.split(",")).toTypedArray()
}

fun parseStringToArrayInt(stringifyArray: String): Array<Int> {
    if (stringifyArray.isEmpty()) return emptyArray()
    return stringifyArray.split(",").map { it.toInt() }.toTypedArray()
}
