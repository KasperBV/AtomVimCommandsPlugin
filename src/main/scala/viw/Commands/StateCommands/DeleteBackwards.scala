package viw.Commands.StateCommands

import viw.Commands.StateCommands.MoveCommands.Left
import viw.internals.State

object DeleteBackwards extends StateCommand {

  override def process(state: State): State = {
    val rState = Left.process(Delete.process(state))
    rState
  }
}
