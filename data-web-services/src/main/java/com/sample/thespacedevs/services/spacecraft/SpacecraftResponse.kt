package com.sample.thespacedevs.services.spacecraft

data class SpacecraftResponse (

	val count : Int,
	val next : String,
	val previous : String,
	val results : List<Results>
)