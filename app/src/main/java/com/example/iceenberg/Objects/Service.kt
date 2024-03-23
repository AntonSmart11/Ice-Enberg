package com.example.iceenberg.Objects

import java.io.Serializable

data class Service(var id: Int, var user_service: String, val equipment_service: Int, val type_service: String, val price: Int, val finished: Int): Serializable