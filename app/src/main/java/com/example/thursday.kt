package com.example.timetable
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import java.util.*


class thursday : Fragment() {
    private lateinit var add: ImageView
    private lateinit var dialog: AlertDialog
    private lateinit var layout: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_thursday, container, false)
        add = view.findViewById(R.id.imgthur)
        layout = view.findViewById(R.id.containerthursday)
        buildDialog()

        add.setOnClickListener {
            dialog.show()
        }
        return view
    }

    private fun buildDialog() {
        var builder = AlertDialog.Builder(requireContext())
        var view = requireActivity().layoutInflater.inflate(R.layout.dialog, null)

        var subject = view.findViewById<EditText>(R.id.subjectid)
        var teacher = view.findViewById<EditText>(R.id.teacherid)
        var block = view.findViewById<EditText>(R.id.blockid)



        var timelokesh=view.findViewById<TimePicker>(R.id.dialogtime)
        var timemin:String=""
        var hourss:Int? = null;
        var minutes:Int? = null;

        timelokesh.setOnTimeChangedListener { _, hourOfDay, minute ->

            timemin =  hourOfDay.toString()+" : "+minute.toString()
            hourss = hourOfDay
            minutes = minute
        }



        builder.setView(view)
            .setTitle("Enter Details")
            .setPositiveButton("OK") { dialog, which ->
                addCard(subject.text.toString(),teacher.text.toString(),block.text.toString(),timemin)



                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourss?: 0)
                selectedTime.set(Calendar.MINUTE, minutes ?: 0)
                selectedTime.set(Calendar.SECOND, 0)
                selectedTime.set(Calendar.MILLISECOND, 0)

                val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(requireActivity(), monday.MyNotificationReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    selectedTime.timeInMillis,
                    pendingIntent
                )



            }
            .setNegativeButton("Cancel") { dialog, which ->

            }

        dialog = builder.create()
    }

    private fun addCard(subject: String,teacher:String,block:String,timemin:String) {
        var view = requireActivity().layoutInflater.inflate(R.layout.card, null)

        val subjectview = view.findViewById<TextView>(R.id.subjectname)
        val teacherview = view.findViewById<TextView>(R.id.teachername)
        val blockview = view.findViewById<TextView>(R.id.block)


        val timepic = view.findViewById<TextView>(R.id.timepic)
        timepic.text = timemin

        val delete = view.findViewById<Button>(R.id.delete)

        subjectview.text = subject
        teacherview.text = teacher
        blockview.text=block


        delete.setOnClickListener {
            layout.removeView(view)
        }

        layout.addView(view)
    }




    class MyNotificationReceiver : BroadcastReceiver() {


        override fun onReceive(context: Context, intent: Intent) {
            val sharedPreferences = context.getSharedPreferences("Event", Context.MODE_PRIVATE)
            val Event = sharedPreferences.getString("EName", null)

            // Create notification
            val channelId = "my_channel_id"
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle("Class Reminder")
                .setContentText("Maths Class")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            // Show notification
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create notification channel
                val channel =
                    NotificationChannel(
                        channelId,
                        "My App Notifications",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0, notificationBuilder.build())
        }
    }


}