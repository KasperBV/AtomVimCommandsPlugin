package viw.Commands.StateCommands

import viw.internals.State

object IndentTwoLines extends StateCommand {

  override def process(state: State): State = {
    val lines = state.contentLines
    if (state.isPositionOnLastLine()) DedentLine.process(state)
    else {
      val line = lines(state.position.line)
      val line2 = lines(state.position.line + 1)
      val newLine = IndentLine.indentLine(line)
      val newLine2 = IndentLine.indentLine(line2)
      val newLines = lines.updated(state.position.line, newLine).updated(state.position.line + 1,newLine2)
      state.copy(
        content = newLines.mkString("\n"),
        position = State.Position(state.position.line, state.position.character + 2)
      )
    }
  }
}
