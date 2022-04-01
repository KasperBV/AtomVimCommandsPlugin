package viw.Commands.StateCommands

import viw.Commands.Command
import viw.EditorState
import viw.internals.State

class ChainCommand(com1: Command, com2: Command) extends Command {

  val command1: Command = com1
  val command2: Command = com2

  override def process(state: State, eState: EditorState): (State, EditorState) = {
    val partiallyProcessedState = command1.process(state, eState)
    command2.process(partiallyProcessedState._1, partiallyProcessedState._2)
  }

  override def equals(x: Any): Boolean = {
    x match {
      case c:ChainCommand => command1 == c.command1 && command2 == c.command2
      case _ => false
    }
  }

}
