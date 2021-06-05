package viw.Commands


import viw.internals.State
import viw.EditorState

object NotBound extends Command{

  override def process(state: State, eState: EditorState): (State, EditorState) = (state, eState)

}
