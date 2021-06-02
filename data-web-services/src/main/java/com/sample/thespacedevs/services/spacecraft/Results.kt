package com.sample.thespacedevs.services.spacecraft

data class Results (

	val id : Int,
	val url : String,
	val name : String,
	val serial_number : String,
	val status : Status,
	val description : String,
	val spacecraft_config : Spacecraft_config
)