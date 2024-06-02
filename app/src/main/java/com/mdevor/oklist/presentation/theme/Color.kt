package com.mdevor.oklist.presentation.theme

import androidx.compose.ui.graphics.Color

val OkListBluePrimary = Color(color = TaskColor.OK_LIST_BLUE.color)
val OkListBlueOnSurface = Color(color = TaskColor.BLUE_INDIGO.color)
val OkListPinkOnSurface = Color(color = TaskColor.PINK.color)
val OkListBeigeOnSurface = Color(color = TaskColor.BEIGE.color)
val OkListWhitePrimary = Color.White
val OkListBlackPrimary = Color.Black
val OkListDarkSurface = Color(color = 0xFF171628)
val OkListGrey700 = Color(color = 0xFF2B2A3C)
val OkListGrey600 = Color(color = 0xFF373856)
val OkListGrey500 = Color(color = 0xFF42445F)
val OkListGrey400 = Color(color = 0xFF4D4F63)
val OkListGrey100 = Color(color = 0xFF9394A0)

enum class TaskColor(val color: Long) {
    OK_LIST_BLUE(0xFF80C8F1),
    PINK(0xFFEB62BC),
    BLUE_INDIGO(0xFF3957C5),
    BEIGE(0xFFFFA992),
}