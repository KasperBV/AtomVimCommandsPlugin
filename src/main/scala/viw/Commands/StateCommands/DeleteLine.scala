package viw.Commands.StateCommands

import viw.internals.State

object DeleteLine extends StateCommand {

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    val newLine = line.substring(0, state.position.character)
    var newLines = lines
    var position = state.position
    if (newLine.length != 0){
      newLines = lines.updated(state.position.line,newLine)
      position = State.Position(state.position.line , state.position.character - 1)
    }
    else{
      if (state.position.line != lines.length -1) {
        newLines = lines.slice(0, state.position.line) ++ lines.slice(state.position.line + 1, lines.length)
        position = state.position
      }
      else {
        newLines =  if (lines.length != 1)
          lines.slice(0, state.position.line) else lines.updated(0, "")
        position = if (lines.length != 1)
          State.Position(state.position.line - 1, state.position.character) else state.position
      }
    }
    val content = newLines.mkString("\n")
    val rState = new State(content, position, state.selection, true)
    rState
  }
}
