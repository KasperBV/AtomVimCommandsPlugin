package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

import scala.annotation.tailrec

object EndOfWord extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    val line = state.contentLines(state.position.line)
    state.position.copy(character = getFirstIndexNextWord(line.substring(state.position.character), state.position.character))
  }

  @tailrec
  private def getFirstIndexNextWord(line: String, count: Int): Int = {
    (line.head, line.tail) match {
      case (_, tail) if tail.head == ' ' => count
      case (head, tail) if tail.length == 1 => count + 1
      case (_, tail) => getFirstIndexNextWord(tail, count + 1)
    }
  }

}
