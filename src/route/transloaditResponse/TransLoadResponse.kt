package com.example.route.transloaditResponse


import com.example.route.transloaditResponse.Fields
import com.example.route.transloaditResponse.Results
import com.example.route.transloaditResponse.Upload
import com.google.gson.annotations.SerializedName

data class TransLoadResponse(
    @SerializedName("account_id")
    val accountId: String? = null,
    @SerializedName("account_name")
    val accountName: String? = null,
    @SerializedName("account_slug")
    val accountSlug: String? = null,
    @SerializedName("assembly_id")
    val assemblyId: String? = null,
    @SerializedName("assembly_ssl_url")
    val assemblySslUrl: String? = null,
    @SerializedName("assembly_url")
    val assemblyUrl: String? = null,
    @SerializedName("build_id")
    val buildId: String? = null,
    @SerializedName("bytes_expected")
    val bytesExpected: Int? = null,
    @SerializedName("bytes_received")
    val bytesReceived: Int? = null,
    @SerializedName("bytes_usage")
    val bytesUsage: Int? = null,
    @SerializedName("client_agent")
    val clientAgent: Any? = null,
    @SerializedName("client_ip")
    val clientIp: Any? = null,
    @SerializedName("client_referer")
    val clientReferer: Any? = null,
    @SerializedName("companion_url")
    val companionUrl: String? = null,
    @SerializedName("executing_jobs")
    val executingJobs: List<Any>? = null,
    @SerializedName("execution_duration")
    val executionDuration: Double? = null,
    @SerializedName("execution_start")
    val executionStart: String? = null,
    @SerializedName("expected_tus_uploads")
    val expectedTusUploads: Int? = null,
    @SerializedName("fields")
    val fields: Fields? = null,
    @SerializedName("finished_tus_uploads")
    val finishedTusUploads: Int? = null,
    @SerializedName("has_dupe_jobs")
    val hasDupeJobs: Boolean? = null,
    @SerializedName("http_code")
    val httpCode: Int? = null,
    @SerializedName("instance")
    val instance: String? = null,
    @SerializedName("is_infinite")
    val isInfinite: Boolean? = null,
    @SerializedName("jobs_queue_duration")
    val jobsQueueDuration: Int? = null,
    @SerializedName("last_job_completed")
    val lastJobCompleted: String? = null,
    @SerializedName("merged_params")
    val mergedParams: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("notify_duration")
    val notifyDuration: Any? = null,
    @SerializedName("notify_response_code")
    val notifyResponseCode: Any? = null,
    @SerializedName("notify_response_data")
    val notifyResponseData: Any? = null,
    @SerializedName("notify_start")
    val notifyStart: Any? = null,
    @SerializedName("notify_url")
    val notifyUrl: Any? = null,
    @SerializedName("ok")
    val ok: String? = null,
    @SerializedName("params")
    val params: String? = null,
    @SerializedName("parent_assembly_status")
    val parentAssemblyStatus: Any? = null,
    @SerializedName("parent_id")
    val parentId: Any? = null,
    @SerializedName("queue_duration")
    val queueDuration: Double? = null,
    @SerializedName("results")
    val results: Results? = null,
    @SerializedName("running_jobs")
    val runningJobs: List<Any>? = null,
    @SerializedName("start_date")
    val startDate: String? = null,
    @SerializedName("started_jobs")
    val startedJobs: List<String>? = null,
    @SerializedName("started_tus_uploads")
    val startedTusUploads: Int? = null,
    @SerializedName("template")
    val template: Any? = null,
    @SerializedName("template_id")
    val templateId: Any? = null,
    @SerializedName("template_name")
    val templateName: Any? = null,
    @SerializedName("transloadit_client")
    val transloaditClient: String? = null,
    @SerializedName("tus_url")
    val tusUrl: String? = null,
    @SerializedName("upload_duration")
    val uploadDuration: Double? = null,
    @SerializedName("upload_meta_data_extracted")
    val uploadMetaDataExtracted: Boolean? = null,
    @SerializedName("uploads")
    val uploads: List<Upload>? = null,
    @SerializedName("uppyserver_url")
    val uppyserverUrl: String? = null,
    @SerializedName("warnings")
    val warnings: List<Any>? = null,
    @SerializedName("websocket_url")
    val websocketUrl: String? = null
)