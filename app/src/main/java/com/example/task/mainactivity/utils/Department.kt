package com.example.task.mainactivity.utils

import androidx.annotation.StringRes
import com.example.task.mainactivity.R

enum class Department(@StringRes val dep: Int) {
    ALL(R.string.all),
    ANDROID(R.string.android),
    IOS(R.string.ios),
    DESIGN(R.string.disign),
    MANAGEMENT(R.string.management),
    QA(R.string.qa),
    BACK_OFFICE(R.string.back_office),
    FRONTEND(R.string.frontend),
    HR(R.string.hr),
    PR(R.string.pr),
    BACKEND(R.string.backend),
    SUPPORT(R.string.support),
    ANALYTICS(R.string.analytics)
}
