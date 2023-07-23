/*
 *   Copyright (c) 2023. Kyrylo Shulzhenko
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package me.kshulzh.farm.ui.common.io

import android.annotation.SuppressLint
import android.app.Activity
import java.io.*

@SuppressLint("StaticFieldLeak")
lateinit var activity: Activity
actual fun writeToFile(file: String): OutputStream {
//    if (SDK_INT >= Build.VERSION_CODES.R) {
//        Log.d("myz", "" + SDK_INT)
//        if (!Environment.isExternalStorageManager()) {
//            ActivityCompat.requestPermissions(
//                activity, arrayOf<String>(
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
//                ), 1
//            ) //permission request code is just an int
//        }
//    } else {
//        if (ActivityCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                activity,
//                arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                1
//            )
//        }
//    }
    val f = File(activity.applicationContext.filesDir, file)
    return FileOutputStream(f)
}

actual fun readFromFile(file: String): InputStream {
    val f = File(activity.applicationContext.filesDir, file)
    return FileInputStream(f)
}