package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

import scala.annotation.tailrec

object NextWord extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    val line = state.contentLines(state.position.line)

    getFirstIndexNextWord(line.substring(state.position.character), state.position.character) match {
      case None => if (state.isPositionOnLastLine()) state.position
      else state.position.copy(line = state.position.line + 1, character = 0)
      case Some(i) => state.position.copy(character = i)
    }
  }

  @tailrec
  private def getFirstIndexNextWord(line: String, count: Int): Option[Int] = {
    line match {
      case line if line.length == 1 => None
      case line if line.head == ' ' && line.tail.head != ' ' => Some(count + 1)
      case _ => getFirstIndexNextWord(line.tail, count + 1)
    }
  }

}
