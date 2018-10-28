package com.keyreply

import android.content.Context
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.view.LayoutInflater
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import org.jetbrains.anko.AnkoLogger
import kotlinx.android.synthetic.main.view_key_reply.view.*
import org.json.JSONObject

class KeyReplyView : RelativeLayout, AnkoLogger {

        companion object {
            val OPEN_COMMAND = "OPEN_CHAT_WINDOW"
            val CLOSE_COMMAND = "CLOSE_CHAT_WINDOW"
            val TOGGLE_COMMAND = "TOGGLE_WINDOW"
            val SEND_MESSAGE_COMMAND = "SEND_MESSAGE"
            val INITIALIZE_COMMAND = "INITIALIZE"
            val DEFAULT_ENV_URL = "https://preview.keyreply.com"
        }

        private lateinit var mContext: Context
        private var mUrl: String = DEFAULT_ENV_URL
        private var mExpanded = true
        private var firstLoad = true
        private var krSetting = JSONObject()

        constructor(context: Context) : super(context) {
            initialize(context)
        }

        constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
            if (attributeSet != null) {
                var typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.KeyReplyView)
                try {
                    mExpanded = typedArray.getBoolean(R.styleable.KeyReplyView_expanded, true)
                } finally {
                    typedArray.recycle()
                }
            }

            initialize(context)
        }

        constructor(context: Context, expanded: Boolean = true) : super(context) {
            mExpanded = expanded
            initialize(context)
        }


        private fun initialize(context: Context) {
            mContext = context
            LayoutInflater.from(mContext).inflate(R.layout.view_key_reply, this, true)

            initializeWebview()
            refreshEnvironmentUrl()
        }


        private fun initializeWebview() {
            web_view.webViewClient = MyWebviewClient()
            web_view.webChromeClient = WebChromeClient()
            web_view.settings.javaScriptEnabled = true
            web_view.settings.domStorageEnabled = true
        }


        private fun refreshEnvironmentUrl() {
            firstLoad = true
            web_view.loadUrl(mUrl)
        }




        private fun showAlert(message: String?, title: String?) {
            if (message == null || message.isNullOrBlank())
                return

            var builder = AlertDialog.Builder(mContext)
            if (title != null && title.isNotBlank())
                builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton("OK") { dialogInterface, p1 ->
                dialogInterface.dismiss()
            }
            builder.create().show()
        }


        private fun runJavascriptCommand(command: String, payload: JSONObject?) {
            if(payload == null) {
                web_view.evaluateJavascript("$" + "keyreply.dispatch('$command')",{ value ->
                    println("value: "+value)
                })
                return
            }

            web_view.evaluateJavascript("$" + "keyreply.dispatch('$command',$payload)",{ value ->
                println("value: "+value)
            })
        }

        inner class MyWebviewClient : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url?.startsWith("keyreplysdk://?") == true) {
                    val subString = url.substringAfter("keyreplysdk://?")
                    val pairParams = subString.split("&")

                    if (pairParams.isEmpty())
                        return true

                    val paramsHashMap = HashMap<String, String>()
                    pairParams.map { it.split("=") }
                        .forEach { paramsHashMap.put(it[0], it[1]) }

                    // Alert
                    if (paramsHashMap.containsKey("alert")) {
                        showAlert(paramsHashMap["alert"], paramsHashMap["title"])
                        return true
                    }

                    return true
                }

                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // set kr_setting
                initServerSetting()

                // Set expanded on first load
                if (firstLoad) {
                    firstLoad = false
                    setExpanded(mExpanded)
                }

            }
        }


        //----------------------------------------------------------------------------------------------
        // Public interfaces
        //----------------------------------------------------------------------------------------------

        /**
         * Set environment
         */
        fun setServerSetting(server: String) {
            krSetting.put("server", server)
        }

        fun setUserSetting(user: JSONObject) {
            krSetting.put("user", user)
        }

        fun initServerSetting() {
            runJavascriptCommand(INITIALIZE_COMMAND, krSetting)
        }

        /**
         * Set env url
         */
        fun setEnvUrl(envUrl: String?) {
            if (envUrl == null || envUrl.isEmpty())
                return

            mUrl = envUrl
            refreshEnvironmentUrl()
        }


        /**
         * Set expanded / collapsed mode
         */
        fun setExpanded(expand: Boolean) {
            var command = ""
            command = when (expand) {
                true -> OPEN_COMMAND
                false -> CLOSE_COMMAND
            }

            runJavascriptCommand(command, null)
        }

        /**
         * Toggle expanded / collapsed mode
         */
        fun toggle() {
            var command = TOGGLE_COMMAND
            runJavascriptCommand(command, null)
        }

        /**
         * Send message
         */
        fun sendMessage(message: String?) {
            if (message.isNullOrBlank())
                return

            var command = SEND_MESSAGE_COMMAND
            var payload = JSONObject()
            payload.put("text",message)
            runJavascriptCommand(command, payload)
        }
}