package com.tminus1010.tmcommonkotlin.speechtotext

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import android.os.Handler
import android.os.Looper
import io.reactivex.rxjava3.core.Single
import org.vosk.Model
import java.io.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Most of this code was copied from [org.vosk.android.StorageService] and adjusted so that assetManager could come from another application.
 */
class ExternalModelProvider(application: Application) {
    val model =
        Single.create<Model> { downstream ->
            unpack(
                application,
                application.packageManager.getResourcesForApplication("com.tminus1010.voskmodelservice").assets,
                "model-en-us",
                "model",
                { downstream.onSuccess(it) },
                { downstream.onError(it) },
            )
        }

    fun unpack(context: Context, assetManager: AssetManager, sourcePath: String, targetPath: String?, completeCallback: (Model) -> Unit, errorCallback: (IOException) -> Unit) {
        val executor: Executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            try {
                val outputPath = sync(context, assetManager, sourcePath, targetPath)
                val model = Model(outputPath)
                handler.post { completeCallback(model) }
            } catch (var8: IOException) {
                handler.post { errorCallback(var8) }
            }
        }
    }

    fun sync(context: Context, assetManager: AssetManager, sourcePath: String, targetPath: String?): String {
        val externalFilesDir = context.getExternalFilesDir(null as String?)
        return if (externalFilesDir == null) {
            throw IOException("cannot get external files dir, external storage state is " + Environment.getExternalStorageState())
        } else {
            val targetDir = File(externalFilesDir, targetPath)
            val resultPath = File(targetDir, sourcePath).absolutePath
            val sourceUUID = readLine(assetManager.open("$sourcePath/uuid"))
            try {
                val targetUUID = readLine(FileInputStream(File(targetDir, "$sourcePath/uuid")))
                if (targetUUID == sourceUUID) {
                    return resultPath
                }
            } catch (var9: FileNotFoundException) {
            }
            deleteContents(targetDir)
            copyAssets(assetManager, sourcePath, targetDir)
            copyFile(assetManager, "$sourcePath/uuid", targetDir)
            resultPath
        }
    }

    private fun readLine(`is`: InputStream): String {
        return BufferedReader(InputStreamReader(`is`)).readLine()
    }

    private fun deleteContents(dir: File): Boolean {
        val files = dir.listFiles()
        var success = true
        if (files != null) {
            val var4 = files.size
            for (var5 in 0 until var4) {
                val file = files[var5]
                if (file.isDirectory) {
                    success = success and deleteContents(file)
                }
                if (!file.delete()) {
                    success = false
                }
            }
        }
        return success
    }

    private fun copyAssets(assetManager: AssetManager, path: String, outPath: File) {
        val assets = assetManager.list(path)
        if (assets != null) {
            if (assets.size == 0) {
                if (!path.endsWith("uuid")) {
                    copyFile(assetManager, path, outPath)
                }
            } else {
                val dir = File(outPath, path)
                if (!dir.exists()) {
                    logz("Making directory " + dir.absolutePath)
                    if (!dir.mkdirs()) {
                        logz("Failed to create directory " + dir.absolutePath)
                    }
                }
                val var5: Array<String> = assets
                val var6 = assets.size
                for (var7 in 0 until var6) {
                    val asset = var5[var7]
                    copyAssets(assetManager, "$path/$asset", outPath)
                }
            }
        }
    }

    private fun copyFile(assetManager: AssetManager, fileName: String, outPath: File) {
        logz("Copy $fileName to $outPath")
        val `in` = assetManager.open(fileName)
        val out: OutputStream = FileOutputStream("$outPath/$fileName")
        val buffer = ByteArray(4000)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
        `in`.close()
        out.close()
    }
}