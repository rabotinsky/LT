package com.skaz.eliot.Services

import com.skaz.eliot.Controller.App
import com.skaz.eliot.Model.MyDate

object UserDataService {

    var fio: String? = ""
    var schet: String? = ""
    var address: String? = ""

    fun logout() {
        fio = ""
        App.prefs.session = ""
    }

    fun getDefStartDate() : MyDate {
        val now = MyDate.now()
        return MyDate(now.year, now.month, 1);
    }

    val defFinishDate = MyDate.now()

    fun dateToStringHuman(year: Int, month: Int, day: Int) : String {
        val months : Array<String> = arrayOf("янв.", "фев.", "мар.", "апр.", "май.", "июн.", "июл.", "авг.", "сен.", "окт.", "ноя.", "дек.")
        return "" + day + " " + months[month] + " " + year
    }

    fun dateToStringHuman(date: MyDate?) : String {
        if (date != null)
            return dateToStringHuman(date.year, date.month, date.day)
        return ""
    }

    fun dateToStrinJson(year: Int, month: Int, day: Int) : String {
        return "" + year + "-" + (month + 1) + "-" + day
    }

    fun dateToStrinJson(date: MyDate?) : String? {
        if (date != null)
            return dateToStrinJson(date.year, date.month, date.day)
        return null
    }
}