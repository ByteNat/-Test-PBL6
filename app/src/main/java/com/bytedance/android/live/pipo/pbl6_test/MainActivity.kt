package com.bytedance.android.live.pipo.pbl6_test

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.android.live.pipo.pbl6_test.billing.PipoInitTask

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addButtonListener()
    }

    private fun clearLog() {
        Runtime.getRuntime().exec("logcat -c");
    }

    private fun clearAndInitiateBillingService() {
        clearLog()
        val task = PipoInitTask()
        task.run(this.baseContext)
    }


    private fun addButtonListener() {
        val button = this.findViewById<Button>(R.id.init_btn)
        button.setOnClickListener {
            clearAndInitiateBillingService()
        }
    }

}