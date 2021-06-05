package viw.Commands.StateCommands

import viw.internals.State

object IndentLine extends StateCommand {

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    val newLine = "  " + line
    val newLines = lines.updated(state.position.line, newLine)
    val content = newLines.mkString("\n")
    new State(content, State.Position(state.position.line, state.position.character+2), state.selection, true)
  }
}
