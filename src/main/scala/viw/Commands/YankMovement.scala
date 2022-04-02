package viw.Commands

import viw.Commands.StateCommands.MoveCommands.MoveCommand
import viw.EditorState
import viw.internals.State

class YankMovement(m : MoveCommand) extends Command {

  val moveCommand : MoveCommand = m

  override def process(state: State, eState: EditorState): (State, EditorState) = {
    val movedState = moveCommand.process(state)
    if (state.position == movedState.position) (state, eState.copy(copyBuffer =  ""))
    else if (state.position > movedState.position)
      yankBetween(eState, state, State.getPositionAfter(state.contentLines, movedState.position).getOrElse(movedState.position),
        State.getPositionAfter(state.contentLines, state.position).getOrElse(state.position))
    else yankBetween(eState, state, state.position, movedState.position)
  }

  def yankBetween(eState: EditorState, state: State, start: State.Position, end: State.Position): (State, EditorState) = {
    val lines = state.contentLines
    val copyBuffer =
        if (start.line != end.line) {
          val startLine = lines(start.line).substring(start.character)
          val endLine = lines(end.line).substring(0, end.character)
          startLine + "\n" + lines.slice(start.line + 1, end.line).mkString("\n") + "\n" + endLine
        } else lines(start.line).substring(start.character , end.character)
    (state, EditorState(eState.lastCommand, eState.viewMode, eState.bindings, copyBuffer))
  }

  override def equals(x: Any): Boolean ={
    x match {
      case y: YankMovement => moveCommand == y.moveCommand
      case _ => false
    }
  }

}
