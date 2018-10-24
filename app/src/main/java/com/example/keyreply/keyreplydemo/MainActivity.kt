package com.example.keyreply.keyreplydemo

import java.util.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Base64
import com.google.common.io.ByteStreams
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {

    private val PICK_IMAGE_REQUEST_CODE = 2018

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
    }


    private fun initializeUI() {
        // KeyReply setup

        key_reply_view.setServerSetting("https://keyreply-platform-demo-bot.azurewebsites.net");
        // Open chat window
        btn_open.setOnClickListener {
            key_reply_view.setExpanded(true)
        }

        // Close chat window
        btn_close.setOnClickListener {
            key_reply_view.setExpanded(false)
        }

        // Toggle chat window
        btn_newtab.setOnClickListener {
            startActivity(Intent(this, NewTabActivity::class.java))
        }

        // Send message
        btn_send_message.setOnClickListener {
            key_reply_view.setExpanded(true)
            key_reply_view.sendMessage("hello there.")
        }

        // Send image
//        btn_send_image.setOnClickListener {
//            pickImageWithPermissionCheck()
//        }
    }


//    @SuppressLint("NeedOnRequestPermissionsResult")
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        onRequestPermissionsResult(requestCode, grantResults)
//    }


//    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//    fun pickImage() {
//        Matisse.from(this)
//            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
//            .countable(false)
//            .maxSelectable(1)
//            .imageEngine(PicassoEngine())
//            .forResult(PICK_IMAGE_REQUEST_CODE)
//    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val selectedUri = Matisse.obtainResult(data)[0]
//            val imageStream = contentResolver.openInputStream(selectedUri)
//            val imageByteArray = ByteStreams.toByteArray(imageStream)
//            val imageEncoded = Base64.encodeToString(imageByteArray, Base64.NO_WRAP)
//            info { "Selected Image Uri: ${selectedUri.toString()}" }
//            info { "Encoded image: $imageEncoded" }
//            key_reply_view.sendImage(imageEncoded)
//        }
//    }
}
