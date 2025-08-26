package com.mekanly.ui.enums

enum class Possibilities(val id: Int, val key: String) {
    WIFI(1, "Wi-Fi"),
    WASHER(2, "Kir ýuwýan maşyn"),
    TV(3, "Televizor"),
    CONDITIONER(4, "Kondisioner"),
    WARDROBE(5, "Şkaf"),
    BED(6, "Krowat"),
    HOT_WATER(7, "Gyzgyn suw"), // Первое вхождение горячей воды
    FRIDGE(8, "Holodilnik"),
    SHOWER(9, "Duş"),
    KITCHEN(10, "Aşhana"),
    MANGAL(11, "Mangal"),
    POOL(12, "Basseýn"),
    KITCHEN_FURNITURE(13, "Aşhana mebeli"),
    BALCONY(14, "Balkon"),
    WORK_DESK(15, "Iş stoly"),
    ELEVATOR(16, "Lift"),
    STOVE(17, "Peç");

    companion object {
        fun fromId(id: Int): Possibilities? = values().find { it.id == id }
    }
}