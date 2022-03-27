package com.sample.thespacedevs.services.spacecraft

data class SpacecraftConfiguration (
	val id : Int,
	val url : String,
	val name : String,
	val type : Type,
	val agency : Agency,
	val in_use : Boolean,
	val image_url: String?
)