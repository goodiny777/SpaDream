package il.co.spadream.spadream

import android.app.Application

import com.onesignal.OneSignal

class AppClass : Application() {


    override fun onCreate() {
        super.onCreate()
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .setNotificationOpenedHandler {
                val additionalData = it.notification.payload.additionalData
                if (additionalData!=null){
                    link = additionalData.getString("link")
                }
            }
            .init()
    }

    companion object{
        var link:String? = null
    }
}

