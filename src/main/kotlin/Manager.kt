import java.util.Scanner
import kotlin.system.exitProcess

class Manager {
    private val archivesList = mutableListOf<Archives>()
    private var currentMenu = MenuType.ARCHIVES
    private var currentNote = Note("", "")
    private var currentArchive = Archives("", mutableListOf())
    private val scanner = Scanner(System.`in`)

    fun showMenu() {
        when (currentMenu) {
            MenuType.ARCHIVES -> {
                println("${currentMenu.typeName}:\n${currentMenu.commandsName}")
                archivesList.forEachIndexed { index, s -> println("${index + 2}. ${s.name}") }
            }

            MenuType.NOTES -> {
                println("${currentMenu.typeName} архива ${currentArchive.name}:\n${currentMenu.commandsName}")
                currentArchive.notes.forEachIndexed { index, s -> println("${index + 2}. ${s.name}") }
            }

            MenuType.NOTE -> println("${currentMenu.typeName} ${currentNote.name}:\n"
                    + "${currentNote.text}\n${currentMenu.commandsName}")
        }
        println("Ввидите номер команды:")
        executeCommand(userInput(InputType.COMMAND).toInt())
    }

    private fun userInput(type: InputType): String {
        if (type == InputType.TEXT) {
            while (true) {
                val input = scanner.nextLine()
                if (input.isBlank()) {
                    println("Поле недолжно быть пустым!!!")
                } else return input
            }
        } else {
            while (true) {
                val input = scanner.nextLine()
                var checkInput = false
                if (input.isNotBlank() && input.all { it.isDigit() }) {
                    checkInput = when (currentMenu) {
                        MenuType.ARCHIVES -> input.toInt() <= archivesList.size + 1
                        MenuType.NOTES -> input.toInt() <= currentArchive.notes.size + 1
                        MenuType.NOTE -> input.toInt() == 0
                    }
                }
                if (checkInput) {
                    return input
                } else println("Ошибка ввода! Ввидите число в соответствии с номером команды!")
            }
        }
    }

    private fun executeCommand(input: Int) {
        when (currentMenu) {
            MenuType.ARCHIVES ->
                when (input) {
                    Commands.EXITorBACK.numberOfCommand -> exitOrBack()
                    Commands.CREATE.numberOfCommand -> createArchive()
                    else -> {
                        val shift = input - 2
                        currentArchive = archivesList[shift]
                        currentMenu = MenuType.NOTES
                    }
                }

            MenuType.NOTES ->
                when (input) {
                    Commands.EXITorBACK.numberOfCommand -> exitOrBack()
                    Commands.CREATE.numberOfCommand -> createNote(currentArchive)
                    else -> {
                        val shift = input - 2
                        currentNote = currentArchive.notes[shift]
                        currentMenu = MenuType.NOTE
                    }
                }

            MenuType.NOTE -> exitOrBack()
        }
    }

    private fun createArchive() {
        println("Введите название архива")
        currentArchive = Archives(userInput(InputType.TEXT), mutableListOf())
        archivesList.add(currentArchive)
    }

    private fun createNote(archives: Archives) {
        println("Введите название заметки")
        val name: String = userInput(InputType.TEXT)
        println("Введите текст заметки")
        val text: String = userInput(InputType.TEXT)
        archives.notes.add(Note(name, text))
    }

    private fun exitOrBack() {
        currentMenu = when (currentMenu) {
            MenuType.ARCHIVES -> {
                println("Пока!")
                exitProcess(1)
            }

            MenuType.NOTES -> MenuType.ARCHIVES
            MenuType.NOTE -> MenuType.NOTES
        }
    }
}

