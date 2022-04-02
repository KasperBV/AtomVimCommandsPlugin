package viw.Commands.DeleteCommands

import viw.Commands.StateCommands.MoveCommands.MoveCommand
import viw.Commands.StateCommands.DeleteLine
import viw.Commands.YankMovement
import viw.EditorState
import viw.internals.State
import viw.internals.State.Position


class DeleteMovement(move: MoveCommand) extends DeleteCommand {

  val movement = move

  override def process(state: State, eState: EditorState): (State, EditorState) = (process(state), new YankMovement(movement).process(state, eState)._2)



  def process(state: State): State = {
    val movedState = movement.process(state)
    if (state.position.equals(movedState.position)) state
    else deleteToNewPosition(state, movedState.position)
  }

  def deleteToNewPosition(state: State, newPosition: Position): State = {
    if (state.position.line == newPosition.line) deleteOnSingleLine(state, newPosition)
    else {
      val stateWithoutInnerLines = deleteLinesBetween(state, math.max(state.position.line, newPosition.line) - 1,
        math.min(state.position.line, newPosition.line) + 1)
      deleteBetweenLines(stateWithoutInnerLines, newPosition, state.position)
    }
  }

  private def deleteOnSingleLine(state: State, newPosition: Position): State = {
    val newLine = deleteOnLineBetween(state.contentLines(state.position.line),
      state.position.character, newPosition.character)
    val newLines = state.contentLines.updated(state.position.line, newLine)
    state.copy(newLines.mkString("\n"),
        state.position.copy(character = math.min(newPosition.character, state.position.character))
    )
  }

  private def deleteOnLineBetween(line: String, first: Int, second: Int): String = {
    if (first > second)
      line.substring(0, second + 1) + line.substring(first + 1)
    else
      line.substring(0, first) + line.substring(second)

  }

  private def deleteLinesBetween(state: State, end: Int, start: Int): State = {
    var rState = new State(state.content, State.Position(start, 0), state.selection, true )
    for (i <- start to end){
      rState = DeleteLine.process(rState)
    }
    state.copy(content = rState.content)
  }

  private def deleteBetweenLines(state: State, end: Position, start: Position): State ={
    if (start.line < end.line) {
      val rState = DeleteLine.process(state)
      val lines = rState.contentLines
      val newLine = lines(rState.position.line).substring(end.character)
      val newLines = lines.updated(rState.position.line, newLine)
      State(newLines.mkString("\n"), State.Position(rState.position.line, 0), state.selection, true)
    }

    else{
      val lines = State.properSplit(state.content)
      var newLines = lines
      val line = lines(end.line)
      val line2 = lines(end.line + 1)
      val newLine = line.substring(0, end.character+1)
      val newLine2 = if (start.character != line2.length - 1)
        line2.substring(start.character + 1) else
        ""
      newLines = lines.updated(end.line, newLine).updated(end.line + 1, newLine2)
      val content = newLines.mkString("\n")
      if (newLine2 == ""){
        val rState = DeleteEntireLine.process(new State(content, State.Position(end.line + 1 ,0), state.selection, true))
        new State(rState.content , end, state.selection, true)
      }
      else new State(content, end, state.selection, true)
    }
  }

  override def equals(x: Any): Boolean ={
    x match {
      case d: DeleteMovement => movement == d.movement
      case _ => false
    }
  }
}