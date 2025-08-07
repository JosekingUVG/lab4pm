package com.example.laboratorio4

data class Mascota(val nombre: String,
                   val especie: String,
                    val raza: String,
                    val edad: Int,
                    val adoptado: Boolean,
                   val imagenResId: Int? = null){

}
