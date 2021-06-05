package viw.Commands.StateCommands

import viw.Commands.StateCommands.MoveCommands.Left
import viw.internals.State

object Delete extends StateCommand {

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    val newLine = line.substring(0, state.position.character) + line.substring(state.position.character + 1)
    val newLines = lines.updated(state.position.line,newLine)
    val content = newLines.mkString("\n")
    val rState = if (line.length - 1 == state.position.character)
      Left.process(new State(content, state.position, state.selection, true)) else
      new State(content, state.position, state.selection, true)
    rState
  }
}
