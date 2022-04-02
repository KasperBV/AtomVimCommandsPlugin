package viw.Commands.StateCommands


import viw.internals.State

object Start extends StateCommand {

  override def process(state: State): State = {
    state.copy(mode = true)
  }
}
