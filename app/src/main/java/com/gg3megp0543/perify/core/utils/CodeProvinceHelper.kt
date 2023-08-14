package com.gg3megp0543.perify.core.utils

class CodeProvinceHelper {
    companion object {
        private val codeProvinceMap = mapOf(
            "ID-AC" to "Aceh",
            "ID-BA" to "Bali",
            "ID-BB" to "Bangka Belitung",
            "ID-BT" to "Banten",
            "ID-BE" to "Bengkulu",
            "ID-JT" to "Jawa Tengah",
            "ID-KT" to "Kalimantan Tengah",
            "ID-ST" to "Sulawesi Tengah",
            "ID-JI" to "Jawa Timur",
            "ID-KI" to "Kalimantan Timur",
            "ID-NT" to "Nusa Tenggara Timur",
            "ID-GO" to "Gorontalo",
            "ID-JK" to "Jakarta",
            "ID-JA" to "Jambi",
            "ID-LA" to "Lampung",
            "ID-MA" to "Maluku",
            "ID-KU" to "Kalimantan Utara",
            "ID-MU" to "Maluku Utara",
            "ID-SA" to "Sulawesi Utara",
            "ID-SU" to "Sumatera Utara",
            "ID-PA" to "Papua",
            "ID-RI" to "Riau",
            "ID-KR" to "Kepulauan Riau",
            "ID-SG" to "Sulawesi Tenggara",
            "ID-KS" to "Kalimantan Selatan",
            "ID-SN" to "Sulawesi Selatan",
            "ID-SS" to "Sumatera Selatan",
            "ID-YO" to "Yogyakarta",
            "ID-JB" to "Jawa Barat",
            "ID-KB" to "Kalimantan Barat",
            "ID-NB" to "Nusa Tenggara Barat",
            "ID-PB" to "Papua Barat",
            "ID-SR" to "Sulawesi Barat",
            "ID-SB" to "Sumatera Barat"
        )

        fun getLocationName(locationCode: String): String {
            return codeProvinceMap[locationCode] ?: ""
        }
    }
}