package com.example

interface Mapper<I,O> {
    fun map(input: I): O
}