package com.mekanly.ui.fragments.addHouse

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class HashtagTextWatcher(private val editText: EditText) : TextWatcher {

    private var isFormatting = false
    private var previousText = ""
    private var cursorPosition = 0

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (!isFormatting) {
            previousText = s?.toString() ?: ""
            cursorPosition = editText.selectionStart
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (s == null || isFormatting) return

        isFormatting = true

        try {
            // Если текст пустой, добавляем первый #
            if (s.isEmpty()) {
                s.append("#")
                editText.setSelection(s.length)
                return
            }

            // Убедимся, что первый символ это #
            if (s[0] != '#') {
                s.insert(0, "#")
                editText.setSelection(cursorPosition + 1)
                return
            }

            // Определяем, было ли удаление текста
            val isDeletion = s.length < previousText.length

            // Проверяем, если пользователь пытается удалить последний # в пустом поле
            if (isDeletion && s.toString() == "") {
                s.append("#")
                editText.setSelection(1)
                return
            }

            // Обработка для пробела
            if (s.last() == ' ') {
                val parts = s.toString().split("#").filter { it.isNotEmpty() }
                val lastPart = parts.lastOrNull()

                // Если последний сегмент пустой или содержит только пробелы
                if (lastPart == null || lastPart.isBlank()) {
                    // Удаляем последний пробел и не добавляем новый хэштег
                    s.delete(s.length - 1, s.length)
                    editText.setSelection(s.length)
                } else {
                    // Если последняя часть непустая и нет уже начатого хэштега
                    if (!s.toString().endsWith(" #")) {
                        s.append("#")
                        editText.setSelection(s.length)
                    }
                }
            }

            // Если удаляется хэштег #, следим чтобы всегда оставался хотя бы один
            if (s.toString().count { it == '#' } == 0) {
                s.insert(0, "#")
                editText.setSelection(1)
            }

        } finally {
            isFormatting = false
        }
    }
}