package com.example.route.transloaditResponse


import com.google.gson.annotations.SerializedName

data class MetaX(
    @SerializedName("aperture")
    val aperture: Double? = null,
    @SerializedName("aspect_ratio")
    val aspectRatio: Double? = null,
    @SerializedName("average_color")
    val averageColor: String? = null,
    @SerializedName("colorspace")
    val colorspace: String? = null,
    @SerializedName("date_file_created")
    val dateFileCreated: String? = null,
    @SerializedName("date_file_modified")
    val dateFileModified: String? = null,
    @SerializedName("date_recorded")
    val dateRecorded: String? = null,
    @SerializedName("device_name")
    val deviceName: String? = null,
    @SerializedName("device_software")
    val deviceSoftware: String? = null,
    @SerializedName("device_vendor")
    val deviceVendor: String? = null,
    @SerializedName("exposure_compensation")
    val exposureCompensation: Int? = null,
    @SerializedName("exposure_mode")
    val exposureMode: String? = null,
    @SerializedName("exposure_time")
    val exposureTime: String? = null,
    @SerializedName("f_number")
    val fNumber: Double? = null,
    @SerializedName("flash")
    val flash: String? = null,
    @SerializedName("focal_length")
    val focalLength: String? = null,
    @SerializedName("frame_count")
    val frameCount: Int? = null,
    @SerializedName("has_clipping_path")
    val hasClippingPath: Boolean? = null,
    @SerializedName("has_transparency")
    val hasTransparency: Boolean? = null,
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("iso")
    val iso: Int? = null,
    @SerializedName("light_value")
    val lightValue: Double? = null,
    @SerializedName("metering_mode")
    val meteringMode: String? = null,
    @SerializedName("orientation")
    val orientation: String? = null,
    @SerializedName("shutter_speed")
    val shutterSpeed: String? = null,
    @SerializedName("white_balance")
    val whiteBalance: String? = null,
    @SerializedName("width")
    val width: Int? = null
)