package com.skaz.eliot.Adapters

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.skaz.eliot.Controller.App
import com.skaz.eliot.Model.Device
import com.skaz.eliot.Model.ElectricIndicationsRequest
import com.skaz.eliot.Model.MyDate
import com.skaz.eliot.Model.WaterIndicationsRequest
import com.skaz.eliot.R
import com.skaz.eliot.Services.DataService
import com.skaz.eliot.Services.UserDataService

class DevicesRecycleAdapter(
    val context: Context,
    devices: List<Device>
) : RecyclerView.Adapter<DevicesRecycleAdapter.Holder>() {
    private val deviceWrappers: List<DeviceWrapper> = devices.map { DeviceWrapper(it) }.toList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.content_main_devices, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        Log.d("SDASDAD", "${deviceWrappers.count()}")
        return deviceWrappers.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindProduct(
            deviceWrappers[position]
        )
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //   val productImage = itemView.findViewById<ImageView>(R.id.deviceImage)
        val deviceTitle = itemView.findViewById<TextView>(R.id.deviceLbl)
        val deviceId = itemView.findViewById<TextView>(R.id.idLbl)
        val deviceSerial = itemView.findViewById<TextView>(R.id.serialLbl)
        val deviceLastAct = itemView.findViewById<TextView>(R.id.lastActLbl)
        val dayUseLbl = itemView.findViewById<TextView>(R.id.dayUseLbl)
        val nightUseLbl = itemView.findViewById<TextView>(R.id.nightUseLbl)
        val allUseLbl = itemView.findViewById<TextView>(R.id.allUseLbl)
        val dateLbl = itemView.findViewById<TextView>(R.id.dateTblTxt)
        val pw_1 = itemView.findViewById<TextView>(R.id.mochNumTxt)
        val pw_2 = itemView.findViewById<TextView>(R.id.mochNumTxt2)
        val pw_3 = itemView.findViewById<TextView>(R.id.mochNumTxt3)
        val amper_1 = itemView.findViewById<TextView>(R.id.amperNumTxt)
        val amper_2 = itemView.findViewById<TextView>(R.id.amperNumTxt2)
        val amper_3 = itemView.findViewById<TextView>(R.id.amperNumTxt3)
        val volt_1 = itemView.findViewById<TextView>(R.id.naprNumTxt)
        val volt_2 = itemView.findViewById<TextView>(R.id.naprNumTxt2)
        val volt_3 = itemView.findViewById<TextView>(R.id.naprNumTxt3)
        val numPhase_1 = itemView.findViewById<TextView>(R.id.phaseNumTxt)
        val numPhase_2 = itemView.findViewById<TextView>(R.id.phaseNumTxt2)
        val numPhase_3 = itemView.findViewById<TextView>(R.id.phaseNumTxt3)
        val beginElectricDurationLbl = itemView.findViewById<TextView>(R.id.beginElectricDurationLbl)
        val endElectricDurationLbl = itemView.findViewById<TextView>(R.id.endElectricDurationLbl)
        val dateLabel = itemView.findViewById<TextView>(R.id.showUseLbl2)
        val phaseLabel = itemView.findViewById<TextView>(R.id.showUseLbl3)
        val tokLabel = itemView.findViewById<TextView>(R.id.showUseLbl4)
        val mochLabel = itemView.findViewById<TextView>(R.id.showUseLbl5)
        val naprLabel = itemView.findViewById<TextView>(R.id.showUseLbl6)
        val constraintLayoutElectrical =
            itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutElectrical)
        val constraintLayoutWater =
            itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutWater)

        val deviceTitleWater = itemView.findViewById<TextView>(R.id.deviceLblWater)
        val deviceIdWater = itemView.findViewById<TextView>(R.id.idLblWater)
        val deviceSerialWater = itemView.findViewById<TextView>(R.id.serialLblWater)
        val deviceLastActWater = itemView.findViewById<TextView>(R.id.lastActLblWater)
        val beginWaterDurationLbl = itemView.findViewById<TextView>(R.id.beginWaterDurationLbl)
        val endWaterDurationLbl = itemView.findViewById<TextView>(R.id.endWaterDurationLbl)
        val icWater = itemView.findViewById<ImageView>(R.id.deviceImageWater)
        val actLbWater = itemView.findViewById<TextView>(R.id.actLbWater)
        val resetElectricDurationBtn = itemView.findViewById<Button>(R.id.resetElectricDurationBtn)
        val showElectricDurationBtn = itemView.findViewById<Button>(R.id.showElectricDurationBtn)
        val resetWaterDurationBtn = itemView.findViewById<Button>(R.id.resetWaterDurationBtn)
        val showWaterDurationBtn = itemView.findViewById<Button>(R.id.showWaterDurationBtn)
        val electricDurationUseLbl = itemView.findViewById<TextView>(R.id.electricDurationUseLbl)
        val durationUseLblWater = itemView.findViewById<TextView>(R.id.durationUseLblWater)
        val cur = itemView.findViewById<TextView>(R.id.textView3Water)
        val notice_msg_electric = itemView.findViewById<TextView>(R.id.notice_msg_electric)
        val notice_msg_water = itemView.findViewById<TextView>(R.id.notice_msg_water)


        fun showElectricDurationStartBtnClicked(wrapper: DeviceWrapper) {
            val dpd =
                DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    beginElectricDurationLbl.text = UserDataService.dateToStringHuman(year, month, day)
                    wrapper.startDate = MyDate(year, month, day)
                    if (wrapper.finishDate == null) {
                        wrapper.finishDate = UserDataService.defFinishDate
                        endWaterDurationLbl.text = UserDataService.dateToStringHuman(wrapper.finishDate)
                    }
                },
                    wrapper.startDate?.year ?: UserDataService.getDefStartDate().year,
                    wrapper.startDate?.month ?: UserDataService.getDefStartDate().month,
                    wrapper.startDate?.day ?: UserDataService.getDefStartDate().day)
            dpd.show()
        }

        fun showElectricDurationEndBtnClicked(wrapper: DeviceWrapper) {
            val dpd =
                DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    endElectricDurationLbl.text = UserDataService.dateToStringHuman(year, month, day)
                    wrapper.finishDate = MyDate(year, month, day)
                    if (wrapper.startDate == null) {
                        wrapper.startDate = UserDataService.getDefStartDate()
                        beginWaterDurationLbl.text = UserDataService.dateToStringHuman(wrapper.startDate)
                    }
                },
                    wrapper.finishDate?.year ?: UserDataService.defFinishDate.year,
                    wrapper.finishDate?.month ?: UserDataService.defFinishDate.month,
                    wrapper.finishDate?.day ?: UserDataService.defFinishDate.day)
            dpd.show()
        }

        fun showWaterDurationStartBtnClicked(wrapper: DeviceWrapper) {
            val dpd =
                DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    beginWaterDurationLbl.text = UserDataService.dateToStringHuman(year, month, day)
                    wrapper.startDate = MyDate(year, month, day)
                    if (wrapper.finishDate == null) {
                        wrapper.finishDate = UserDataService.defFinishDate
                        endWaterDurationLbl.text = UserDataService.dateToStringHuman(wrapper.finishDate)
                    }
                },
                    wrapper.startDate?.year ?: UserDataService.getDefStartDate().year,
                    wrapper.startDate?.month ?: UserDataService.getDefStartDate().month,
                    wrapper.startDate?.day ?: UserDataService.getDefStartDate().day)
            dpd.show()
        }

        fun showWaterDurationEndBtnClicked(wrapper: DeviceWrapper) {
            val dpd =
                DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    endWaterDurationLbl.text = UserDataService.dateToStringHuman(year, month, day)
                    wrapper.finishDate = MyDate(year, month, day)
                    if (wrapper.startDate == null) {
                        wrapper.startDate = UserDataService.getDefStartDate()
                        beginWaterDurationLbl.text = UserDataService.dateToStringHuman(wrapper.startDate)
                    }
                },
                    wrapper.finishDate?.year ?: UserDataService.defFinishDate.year,
                    wrapper.finishDate?.month ?: UserDataService.defFinishDate.month,
                    wrapper.finishDate?.day ?: UserDataService.defFinishDate.day)
            dpd.show()
        }

        fun electricRequest(wrapper: DeviceWrapper, messageText: String) {
            electricDurationUseLbl.text = messageText
            val request = ElectricIndicationsRequest(
                App.prefs.session,
                wrapper.device.id.toString(),
                UserDataService.dateToStrinJson(wrapper.startDate),
                UserDataService.dateToStrinJson(wrapper.finishDate)
            )
            DataService.electricIndicationsRequest(request) { response ->
                if (response == null) {

                } else if (response.error != null) {
                    showAlter(response.error)
                } else {
                    if (response.notice != null) {
                        notice_msg_electric.visibility = View.VISIBLE
                        notice_msg_electric.text = response.notice
                    } else {
                        notice_msg_electric.visibility = View.GONE
                        notice_msg_electric.text = ""
                    }
                    dayUseLbl.text = "${response.t1.format(2)} кВт*ч"
                    nightUseLbl.text = "${response.t2.format(2)} кВт*ч"
                    allUseLbl.text = "${(response.t1 + response.t2).format(2)} кВт*ч"
                }
            }
        }

        private fun Double.format(digits: Int) = "%.${digits}f".format(this)

        fun waterRequest(wrapper: DeviceWrapper, messageText: String) {
            durationUseLblWater.text = messageText
            val request = WaterIndicationsRequest(
                App.prefs.session,
                wrapper.device.id.toString(),
                UserDataService.dateToStrinJson(wrapper.startDate),
                UserDataService.dateToStrinJson(wrapper.finishDate)
            )
            DataService.waterIndicationsRequest(request) { response ->
                if (response == null) {

                } else if (response.error != null) {
                    showAlter(response.error)
                } else {
                    if (response.notice != null) {
                        notice_msg_water.visibility = View.VISIBLE
                        notice_msg_water.text = response.notice
                    } else {
                        notice_msg_water.visibility = View.GONE
                        notice_msg_water.text = ""
                    }
                    cur.text = "${response.value} М³"
                }
            }
        }

        fun bindProduct(
            wrapper: DeviceWrapper
        ) {

            if (wrapper.device.category == "ee") {
                constraintLayoutElectrical.visibility = View.VISIBLE
                constraintLayoutWater.visibility = View.GONE
                cur.text = ""
                notice_msg_electric.visibility = View.GONE
                showElectricDurationBtn.setOnClickListener {
                    electricRequest(wrapper, "Потребление за период")
                }
                resetElectricDurationBtn.setOnClickListener {
                    wrapper.startDate = null
                    wrapper.finishDate = null
                    beginElectricDurationLbl.text = UserDataService.dateToStringHuman(wrapper.startDate ?: UserDataService.getDefStartDate())
                    endElectricDurationLbl.text = UserDataService.dateToStringHuman(wrapper.finishDate ?: UserDataService.defFinishDate)
                    electricRequest(wrapper, "Потребление за все время")
                }
                beginElectricDurationLbl.setOnClickListener{
                    showElectricDurationStartBtnClicked(wrapper)
                }
                endElectricDurationLbl.setOnClickListener{
                    showElectricDurationEndBtnClicked(wrapper)
                }
            } else if (wrapper.device.category == "water") {
                constraintLayoutElectrical.visibility = View.GONE
                constraintLayoutWater.visibility = View.VISIBLE
                notice_msg_water.visibility = View.GONE
                if (wrapper.device.device_info.isNotEmpty()) {
                    when {
                        wrapper.device.device_info[0].type == "hot" -> icWater.setImageResource(R.drawable.red)
                        else -> icWater.setImageResource(R.drawable.drop)
                    }
                    deviceTitleWater.text = wrapper.device.type
                    deviceIdWater.text = "ID: ${wrapper.device.id} |"
                    deviceSerialWater.text = " Серийный номер: ${wrapper.device.device_info[0].serial}"
                    deviceLastActWater.text =
                        "Последняя активность: ${if (wrapper.device.device_info != null) wrapper.device.device_info[0].last_act else ""}"
                    actLbWater.text =
                        "Показания от: ${wrapper.device.last_data?.date ?: ""}" //TODO ne otobrazhaet
                }
                if (wrapper.device.last_data != null) {
                    cur.text = "${wrapper.device.last_data?.cur} М³"
                }

                showWaterDurationBtn.setOnClickListener {
                    waterRequest(wrapper, "Потребление за период")
                }
                resetWaterDurationBtn.setOnClickListener{
                    wrapper.startDate = null
                    wrapper.finishDate = null
                    beginWaterDurationLbl.text = UserDataService.dateToStringHuman(wrapper.startDate ?: UserDataService.getDefStartDate())
                    endWaterDurationLbl.text = UserDataService.dateToStringHuman(wrapper.finishDate ?: UserDataService.defFinishDate)
                    waterRequest(wrapper, "Потребление за все время")
                }

                beginWaterDurationLbl.setOnClickListener{
                    showWaterDurationStartBtnClicked(wrapper)
                }
                endWaterDurationLbl.setOnClickListener{
                    showWaterDurationEndBtnClicked(wrapper)
                }
            }

            deviceTitle.text = wrapper.device.type
            deviceId.text = "ID: ${wrapper.device.id} |"
            deviceSerial.text =
                " Серийный номер: ${if (wrapper.device.device_info != null) wrapper.device.device_info[0].serial else ""}"
            deviceLastAct.text =
                "Последняя активность: ${if (wrapper.device.device_info != null) wrapper.device.device_info[0].last_act else ""}"
            dayUseLbl.text =
                "${if (wrapper.device.accumulated_en != null) wrapper.device.accumulated_en!!.t1.format(2) else ""} кВт*ч"
            nightUseLbl.text =
                "${if (wrapper.device.accumulated_en != null) wrapper.device.accumulated_en!!.t2.format(2) else ""} кВт*ч"
            allUseLbl.text =
                "${if (wrapper.device.accumulated_en != null) (wrapper.device.accumulated_en!!.t1 + wrapper.device.accumulated_en!!.t2).format(2) else ""} кВт*ч" //Todo summa klvt
            dateLbl.text = wrapper.device.last_data?.date
            pw_1.text = wrapper.device.last_data?.pw_1?.toString()
            pw_2.text = wrapper.device.last_data?.pw_2?.toString()
            pw_3.text = wrapper.device.last_data?.pw_3?.toString()
            amper_1.text = wrapper.device.last_data?.amper_1?.toString() ?: "-"
            amper_2.text = wrapper.device.last_data?.amper_2?.toString() ?: "-"
            amper_3.text = wrapper.device.last_data?.amper_3?.toString() ?: "-"
            volt_1.text = wrapper.device.last_data?.volt_1?.toString() ?: "-"
            volt_2.text = wrapper.device.last_data?.volt_2?.toString() ?: "-"
            volt_3.text = wrapper.device.last_data?.volt_3?.toString() ?: "-"
            numPhase_1.text = "1"
            numPhase_2.text = "2"
            numPhase_3.text = "3"
            beginWaterDurationLbl.text = UserDataService.dateToStringHuman(UserDataService.getDefStartDate())
            endWaterDurationLbl.text = UserDataService.dateToStringHuman(UserDataService.defFinishDate)
            beginElectricDurationLbl.text = UserDataService.dateToStringHuman(UserDataService.getDefStartDate())
            endElectricDurationLbl.text = UserDataService.dateToStringHuman(UserDataService.defFinishDate)
        }
    }

    private fun showAlter(text: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Ошибка")
        builder.setMessage(text)
        builder.setPositiveButton("Хорошо") { _, _ ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}