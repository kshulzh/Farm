package me.kshulzh.farm.ui.common.components

import org.lwjgl.system.MemoryStack
import org.lwjgl.util.tinyfd.TinyFileDialogs
import org.lwjgl.util.tinyfd.TinyFileDialogs.tinyfd_selectFolderDialog
import javax.swing.JFileChooser
import javax.swing.UIManager
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * This code is derived from the terrific PACMC project:
 * https://github.com/jakobkmar/pacmc
 */
internal object FilePicker {
    private enum class CallType {
        FILE,
        DIRECTORY
    }

    fun chooseFile(
        initialDirectory: String = System.getProperty("user.dir"),
        fileExtensions: String = "",
        multiple: Boolean = false
    ): List<String>? {
        return chooseFile(CallType.FILE, initialDirectory, fileExtensions, multiple)
    }

    fun chooseDirectory(
        initialDirectory: String = System.getProperty("user.dir"),
        multiple: Boolean = false
    ): List<String>? {
        return chooseFile(CallType.DIRECTORY, initialDirectory, multiple = multiple)
    }

    private fun chooseFile(
        type: CallType,
        initialDirectory: String,
        fileExtensions: String = "",
        multiple: Boolean = false
    ): List<String>? {
        return kotlin.runCatching { chooseFileNative(type, initialDirectory, fileExtensions, multiple) }
            .onFailure { nativeException ->
                println("A call to chooseDirectoryNative failed: ${nativeException.message}")

                kotlin.runCatching { chooseFileSwing(type, initialDirectory, fileExtensions) }
                    .onFailure { swingException ->
                        println("A call to chooseDirectorySwing failed ${swingException.message}")
                    }
                    .getOrNull()
            }
            .getOrNull()
            ?.split("|")
    }

    private fun chooseFileNative(
        type: CallType,
        initialDirectory: String,
        fileExtension: String,
        multiple: Boolean = false
    ): String? {
        return when (type) {
            CallType.FILE -> {
                MemoryStack.stackPush().use { stack ->
                    val filters = if (fileExtension.isNotEmpty()) fileExtension.split(",") else emptyList()
                    val aFilterPatterns = stack.mallocPointer(filters.size)
                    filters.forEach {
                        aFilterPatterns.put(stack.UTF8("*.$it"))
                    }
                    aFilterPatterns.flip()
                    TinyFileDialogs.tinyfd_openFileDialog(
                        "Choose File",
                        initialDirectory,
                        aFilterPatterns,
                        null,
                        multiple
                    )
                }
            }

            CallType.DIRECTORY -> {
                tinyfd_selectFolderDialog(
                    "Choose Directory",
                    initialDirectory
                )
            }
        }
    }

    private fun chooseFileSwing(
        type: CallType,
        initialDirectory: String,
        fileExtension: String
    ): String? {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        val chooser = when (type) {
            CallType.FILE -> {
                JFileChooser(initialDirectory).apply {
                    fileSelectionMode = JFileChooser.FILES_ONLY
                    acceptAllFileFilter
                    isVisible = true
                    addChoosableFileFilter(FileNameExtensionFilter(fileExtension, fileExtension))
                }
            }

            CallType.DIRECTORY -> {
                JFileChooser(initialDirectory).apply {
                    fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                    isVisible = true
                }
            }
        }

        return when (val code = chooser.showOpenDialog(null)) {
            JFileChooser.APPROVE_OPTION -> chooser.selectedFile.absolutePath
            JFileChooser.CANCEL_OPTION -> null
            JFileChooser.ERROR_OPTION -> error("An error occurred while executing JFileChooser::showOpenDialog")
            else -> error("Unknown return code '${code}' from JFileChooser::showOpenDialog")
        }
    }
}