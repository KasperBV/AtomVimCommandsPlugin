package viw.Commands.StateCommands

import viw.internals.State

object JoinLine extends StateCommand {

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    if (state.position.line != lines.length - 1) {
      val newLine = line + " " + lines(state.position.line + 1)
      val position = State.Position(state.position.line, line.length)
      var newLines = lines.updated(state.position.line, newLine)
      newLines = newLines.filter(x => newLines.indexOf(x) != state.position.line + 1)
      val content = newLines.mkString("\n")
      new State(content, position, state.selection, true)
    }
    else state
  }


}
