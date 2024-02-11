package com.example.moviedd.data.database.model

data class TrieNode(
    val value: String = "",
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
) {
    var isEndOfWord = false
}
