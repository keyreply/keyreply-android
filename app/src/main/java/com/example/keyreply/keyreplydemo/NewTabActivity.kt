package com.example.keyreply.keyreplydemo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_new_tab.*

class NewTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tab)
        new_key_reply_view.setServerSetting("https://keyreply-platform-demo-bot.azurewebsites.net");
        setSupportActionBar(mainToolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "New Tab"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
