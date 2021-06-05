package viw.Commands

import viw.Commands.DeleteCommands.DeleteCommand
import viw.Commands.StateCommands.Exit
import viw.EditorState
import viw.internals.State

class ChangeMovement(del: DeleteCommand) extends Command {

  val delete = del

  override def process(state: State, eState: EditorState): (State, EditorState) = {
    val newStates = delete.process(state, eState)
    (Exit.process(newStates._1), newStates._2)
  }

  override def equals(x: Any): Boolean ={
    x match {
      case c: ChangeMovement => delete == c.delete
      case _ => false
    }
  }
}
