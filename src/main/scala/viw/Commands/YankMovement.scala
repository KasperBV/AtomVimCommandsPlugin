package viw.Commands

import viw.Commands.StateCommands.MoveCommands.MoveCommand
import viw.EditorState
import viw.internals.State

class YankMovement(m : MoveCommand) extends Command {


  val moveCommand : MoveCommand = m

  override def process(state: State, eState: EditorState): (State, EditorState) = {
    val movedState = moveCommand.process(state)
    val lines = State.properSplit(state.content)
    val start = if (state.position < movedState.position) state.position else movedState.position
    val end = if (state.position > movedState.position) state.position else movedState.position

    val copyBuffer =
      if (state.position < movedState.position) {
        if (start.line != end.line) {
          val startLine = lines(start.line).substring(start.character)
          val endLine = lines(end.line).substring(0, end.character)
          startLine + "\n" + lines.slice(start.line + 1, end.line).mkString("\n") + "\n" + endLine
        }
        else if (start.character != end.character) lines(start.line).substring(start.character , end.character)
        else eState.copyBuffer
      }
      else {
        if (start.line != end.line) {
          val startLine = lines(start.line).substring(start.character+1)
          val endLine = lines(end.line).substring(0, end.character+1)
          startLine + "\n" + lines.slice(start.line + 1, end.line).mkString("\n") + "\n" + endLine
        }
      else if (start.character != end.character) lines(start.line).substring(start.character+1 , end.character+1)
      else eState.copyBuffer

      }
    (state, EditorState(eState.lastCommand, eState.viewMode, eState.bindings, copyBuffer))

  }

  override def equals(x: Any): Boolean ={
    x match {
      case y: YankMovement => moveCommand == y.moveCommand
      case _ => false
    }
  }
}
