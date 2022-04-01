package viw.Commands.StateCommands

import viw.internals.State

object DedentLine extends StateCommand {

  override def process(state: State): State = {
    val line = state.contentLines(state.position.line)
    val newLine = dedentLine(line)
    val newLines = state.contentLines.updated(state.position.line, newLine)
    state.copy(
      content = newLines.mkString("\n"),
      position = State.Position(state.position.line, state.position.character - (line.length - newLine.length))
    )
  }

  private[Commands] def dedentLine(line: String): String ={
    line match {
      case line if (line.head == ' ' && line.length == 1) => line.substring(1)
      case line if (line.head == ' ') => line.substring(2)
      case _ => line
    }
  }

}
