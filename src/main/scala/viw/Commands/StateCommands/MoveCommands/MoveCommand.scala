package viw.Commands.StateCommands.MoveCommands

import viw.Commands.StateCommands.StateCommand
import viw.internals.State

abstract class MoveCommand extends StateCommand{

  override def process(state: State): State = {
    new State(state.content, getNewPosition(state), state.selection, true)
  }

  def getNewPosition(state: State): State.Position

}







