package viw.Commands.StateCommands


import viw.internals.State

object Start extends StateCommand {

  override def process(state: State): State = {
    new State(state.content, state.position, state.selection, true)
  }
}
