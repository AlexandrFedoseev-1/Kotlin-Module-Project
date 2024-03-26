enum class MenuType(val typeName: String, val commandsName: String) {
    ARCHIVES("Архивы", "0. Выход\n1. Создать архив"),
    NOTES("Заметки", "0. Назад\n1. Создать заметку"),
    NOTE("Заметка", "0. Назад")
}

enum class InputType {
    COMMAND,
    TEXT
}

enum class Commands(val numberOfCommand: Int) {
    EXITorBACK(0),
    CREATE(1)
}

data class Archives(val name: String, val notes: MutableList<Note>)
data class Note(val name: String, val text: String)