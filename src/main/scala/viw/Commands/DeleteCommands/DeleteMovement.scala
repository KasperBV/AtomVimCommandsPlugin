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
    var rState = state
    if (state.position.equals(movedState.position)){
      state
    }
    else if (state.position.line == movedState.position.line) {
      deleteBetween(state, movedState.position.character,state.position.character)
    }
    else {
      if (math.abs(state.position.line - movedState.position.line) > 1)
        rState = deleteLinesBetween(state, math.max(state.position.line, movedState.position.line) - 1, math.min(state.position.line, movedState.position.line) + 1)
        rState = deleteBetweenLines(rState, movedState.position, state.position)
        rState
    }
  }

  private def deleteBetween(state: State, end: Int, start: Int): State = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    var newLine = line
    var position = state.position
    if (start < end){
      newLine = line.substring(0, start) + line.substring(end)
      position = State.Position(state.position.line, start)
    }
    else {
      newLine = line.substring(0, end+1) + line.substring(start+1)
      position = State.Position(state.position.line, end)
    }
    val newLines = lines.updated(state.position.line,newLine)
    val content = newLines.mkString("\n")

    val rState = new State(content, position, state.selection, true)
    rState
  }

  private def deleteBetweenLines(state: State, end: Position, start: Position): State ={

    if (start.line < end.line) {
      val rState = DeleteLine.process(state)
      val lines = State.properSplit(rState.content)
      var newLines = lines
      var position = state.position
      if (state.position.character != 0) {
        val line = lines(start.line + 1)
        val newLine = line.substring(end.character)
        newLines = lines.updated(state.position.line + 1, newLine)
        position = State.Position(start.line + 1, 0)
      }
      else {
        val line = lines(start.line)
        val newLine = line.substring(end.character)
        newLines = lines.updated(state.position.line, newLine)
        position = State.Position(start.line, 0)
      }
      val content = newLines.mkString("\n")
      new State(content, position, state.selection, true)
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
      newLines = lines.updated(end.line, newLine)
      newLines = newLines.updated(end.line + 1, newLine2)
      val content = newLines.mkString("\n")
      if (newLine2 == ""){
        val rState = DeleteEntireLine.process(new State(content, State.Position(end.line + 1 ,0), state.selection, true))
        new State(rState.content , end, state.selection, true)
      }
      else new State(content, end, state.selection, true)
    }
  }

  private def deleteLinesBetween(state: State, end: Int, start: Int): State = {
    var rState = new State(state.content, State.Position(start, 0), state.selection, true )
    for (i <- start to end){
      rState = DeleteLine.process(rState)
    }
    new State(rState.content, state.position, state.selection, true)
  }

  override def equals(x: Any): Boolean ={
    x match {
      case d: DeleteMovement => movement == d.movement
      case _ => false
    }
  }
}