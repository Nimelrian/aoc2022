package de.nimelrian.aoc2022

interface From<T, Self> {
    fun from(source: T): Self
    fun T.into(): Self = from(this)
}
