package viw.Commands.StateCommands

import viw.Commands.Command
import viw.EditorState
import viw.internals.State

abstract class StateCommand extends Command{

  override def process(state: State, eState: EditorState): (State, EditorState) = (process(state), eState)

  def process (state: State): State

}
