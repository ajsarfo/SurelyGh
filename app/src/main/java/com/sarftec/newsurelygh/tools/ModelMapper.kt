package com.sarftec.newsurelygh.tools

interface ModelMapper<in T, out U> {
    fun map(from: T) : U
}