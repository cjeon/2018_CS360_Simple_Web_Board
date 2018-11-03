package com.example.dbprj.entities

import javax.persistence.*

@Entity
@Table(name = "Url")
data class Url(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int,
               var url: String)