package co.cimarrones.bodega.httpService

import kotlinx.serialization.json.Json

val JsonParserConfig = Json {
    coerceInputValues = true
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
}