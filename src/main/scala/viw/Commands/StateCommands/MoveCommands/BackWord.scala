package viw.Commands.StateCommands.MoveCommands

import viw.internals.State
import viw.internals.State.Position

import scala.annotation.tailrec

object BackWord extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)

    if (state.isPositionAtStartOfLine())
      if (state.isPositionOnFirstLine()) state.position
      else Position(state.position.line - 1, previousWord(lines(state.position.line - 1)))
    else state.position.copy(character = previousWord(line.substring(0, state.position.character)))
  }

  @tailrec
  private def previousWord(line: String): Int = {
    line match {
      case line if line.length == 1 => 0
      case line if line.last != ' ' && line(line.length-2) == ' ' => line.length - 1
      case _ => previousWord(line.dropRight(1))
    }
  }

}
