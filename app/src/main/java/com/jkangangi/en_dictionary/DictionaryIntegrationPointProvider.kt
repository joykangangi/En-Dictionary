package com.jkangangi.en_dictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.bumble.appyx.core.integrationpoint.ActivityIntegrationPoint
import com.bumble.appyx.core.integrationpoint.IntegrationPointProvider

/**
 *
 * Using `NodeComponentActivity` causes a build error, appyx current version does not support latest compose version:(:
 *  -Inherited platform declarations clash: The following declarations have the same JVM signature (onRequestPermissionsResult(I[Ljava/lang/String;[I)V):
 *     fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray): Unit defined in appyx.MainActivity
 *     fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Unit defined in .appyx.MainActivity
 *
 * Inheriting [IntegrationPointProvider] manually seems to help.
 */

open class DictionaryIntegrationPointProvider : ComponentActivity(), IntegrationPointProvider {

    override lateinit var appyxV1IntegrationPoint: ActivityIntegrationPoint
        protected set

    protected open fun createIntegrationPoint(savedInstanceState: Bundle?) =
        ActivityIntegrationPoint(
            activity = this,
            savedInstanceState = savedInstanceState
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appyxV1IntegrationPoint = createIntegrationPoint(savedInstanceState)
    }

//    @Suppress("OVERRIDE_DEPRECATION")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        appyxV1IntegrationPoint.onActivityResult(requestCode, resultCode, data)
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        appyxV1IntegrationPoint.onSaveInstanceState(outState)
//    }
}