package viw.Commands.StateCommands

import viw.internals.State

object IndentTwoLines extends StateCommand {

  override def process(state: State): State = {

    val lines = State.properSplit(state.content)
    if (state.position.line < lines.length - 1) {
      val line = lines(state.position.line)
      val line2 = lines(state.position.line + 1)
      val newLine = "  " + line
      val newLine2 = "  " + line2
      val newLines = lines.updated(state.position.line, newLine).updated(state.position.line+1, newLine2)
      val content = newLines.mkString("\n")
      new State(content, State.Position(state.position.line, state.position.character+2), state.selection, true)
    }
    else {
      IndentLine.process(state)
    }
  }
}
