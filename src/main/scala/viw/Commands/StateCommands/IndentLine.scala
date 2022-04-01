package viw.Commands.StateCommands

import viw.internals.State

object IndentLine extends StateCommand {

  override def process(state: State): State = {
    val line = state.contentLines(state.position.line)
    val newLine = indentLine(line)
    val newLines = state.contentLines.updated(state.position.line, newLine)
    state.copy(
      content = newLines.mkString("\n"),
      position = State.Position(state.position.line, state.position.character + 2)
    )
  }

  private[Commands] def indentLine(line: String): String ={
    "  " + line
  }


}
