package viw.Commands.EditorCommands
import viw.{EditorState}
import viw.Commands.Command
import viw.internals.State

abstract class  EditorCommand extends Command{

  override def process(state: State, eState: EditorState): (State, EditorState) = (state, process(eState))

  def process (eState: EditorState): EditorState
}
