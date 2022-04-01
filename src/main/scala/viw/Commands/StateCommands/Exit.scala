package viw.Commands.StateCommands

import viw.internals.State

object Exit extends StateCommand {

  override def process(state: State): State = {
    State(state.content, state.position, state.selection, false)
  }

}
