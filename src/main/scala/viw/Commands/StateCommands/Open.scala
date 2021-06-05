package viw.Commands.StateCommands

import viw.internals.State

object Open extends StateCommand {

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    val newLine = line + "\n"
    val newLines = lines.updated(state.position.line, newLine)
    val content = newLines.mkString("\n")
    val position = State.Position(state.position.line + 1, 0)
    new State(content, position, state.selection, false)
  }
}
