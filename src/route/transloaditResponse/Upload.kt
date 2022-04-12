package com.example.route.transloaditResponse


import com.example.route.transloaditResponse.MetaX
import com.google.gson.annotations.SerializedName

data class Upload(
    @SerializedName("basename")
    val basename: String? = null,
    @SerializedName("ext")
    val ext: String? = null,
    @SerializedName("field")
    val `field`: String? = null,
    @SerializedName("from_batch_import")
    val fromBatchImport: Boolean? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("is_tus_file")
    val isTusFile: Boolean? = null,
    @SerializedName("md5hash")
    val md5hash: String? = null,
    @SerializedName("meta")
    val meta: MetaX? = null,
    @SerializedName("mime")
    val mime: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_basename")
    val originalBasename: String? = null,
    @SerializedName("original_id")
    val originalId: String? = null,
    @SerializedName("original_md5hash")
    val originalMd5hash: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("original_path")
    val originalPath: String? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("ssl_url")
    val sslUrl: String? = null,
    @SerializedName("tus_upload_url")
    val tusUploadUrl: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null
)