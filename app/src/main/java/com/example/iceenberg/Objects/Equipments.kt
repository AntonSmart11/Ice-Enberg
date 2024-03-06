package com.example.iceenberg.Objects

import java.io.Serializable

data class Equipments(var id: Int, var user: Int, var name: String, var location: String, var direction: String, var brand: String, var model: String):
    Serializable