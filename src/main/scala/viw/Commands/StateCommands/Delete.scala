package viw.Commands.StateCommands

import viw.internals.State

object Delete extends StateCommand {

  override def process(state: State): State = {
    val lines = state.contentLines
    val line = lines(state.position.line)
    val newLine = line.substring(0, state.position.character) + line.substring(state.position.character + 1)
    val newLines = lines.updated(state.position.line,newLine)
    val newContent = newLines.mkString("\n")
    if (line.length - 1 == state.position.character)
      state.copy(content = newContent, position = state.position.copy(character = state.position.character - 1))
    else state.copy(content = newContent)
  }
}
