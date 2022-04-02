package viw.Commands.StateCommands

import viw.internals.State

object JoinLine extends StateCommand {

  override def process(state: State): State = {
    if (state.isPositionOnLastLine()) state
    else joinTwoLines(state)
  }

  def joinTwoLines(state: State): State ={
    val currentLine = state.contentLines(state.position.line)
    val nextLine = state.contentLines(state.position.line + 1)
    val newLine = currentLine + " " + nextLine
    val newLines = state.contentLines.updated(state.position.line, newLine)
      .filter(_ != nextLine)
    state.copy(content = newLines.mkString("\n"), position = state.position.copy(character = currentLine.length))
  }

}
