package com.ankn.cryptocurrencyminiapp.presentation.utils.extensions

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView

fun TextView.setColoredText(
    fullText: String,
    targetText: String,
    primaryColor: Int,
    targetColor: Int,
    boldTarget: Boolean = false
) {
    // สร้าง SpannableString จาก fullText
    val spannableString = SpannableString(fullText)

    // ตั้งค่าสีหลักให้กับข้อความทั้งหมด
    spannableString.setSpan(
        ForegroundColorSpan(primaryColor),
        0,
        fullText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    // ค้นหาตำแหน่งของ targetText
    val startIndex = fullText.indexOf(targetText)
    if (startIndex != -1) {
        val endIndex = startIndex + targetText.length

        // ตั้งค่าสี target ให้กับ targetText
        spannableString.setSpan(
            ForegroundColorSpan(targetColor),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // ถ้า boldTarget เป็น true ให้ตั้งค่าให้ targetText เป็นตัวหนา
        if (boldTarget) {
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    // กำหนดข้อความที่มี span ให้กับ TextView
    this.text = spannableString
}
