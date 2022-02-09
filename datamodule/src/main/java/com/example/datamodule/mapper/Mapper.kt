package com.example.datamodule.mapper

interface Mapper<From, To> {

    fun map(from: From): To

}