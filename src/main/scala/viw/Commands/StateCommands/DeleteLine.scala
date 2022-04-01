package viw.Commands.StateCommands

import viw.internals.State

object DeleteLine extends StateCommand {



  override def process(state: State): State = {
    if (state.isPositionAtStartOfLine()) cutOutLine(state)
    else cutLineAtCurrentPosition(state)
  }

  def cutOutLine(state: State): State = {
    val lines = state.contentLines
    if (state.isPositionOnLastLine()) {
      state.copy(content = dropLastLine(lines).mkString("\n"), position = State.Position(Math.max(0, lines.length - 2), 0))
    } else {
      state.copy(content = dropLine(lines, state.position.line).mkString("\n"), position = State.Position(state.position.line, 0))
    }
  }

  def dropLastLine(lines: Vector[String]): Vector[String] ={
    if (lines.length != 1) lines.slice(0, lines.length - 1)
    else lines.updated(0, "")
  }

  def dropLine(lines: Vector[String], lineIndex: Int): Vector[String] ={
    lines.slice(0, lineIndex) ++ lines.slice(lineIndex + 1, lines.length)
  }

  def cutLineAtCurrentPosition(state: State): State = {
    val lines = state.contentLines
    if (state.isPositionAtEndOfLine()) state
    else {
      state.copy(
        content = trimLine(lines, state.position.line, state.position.character).mkString("\n"),
        position = state.position.copy(character = state.position.character - 1)
      )
    }
  }

  def trimLine(lines: Vector[String], lineIndex: Int, characterIndex: Int): Vector[String] ={
    val newLine = lines(lineIndex).substring(0, characterIndex)
    lines.updated(lineIndex,newLine)
  }


}
