package viw.Commands.StateCommands

import viw.internals.State

object DedentLine extends StateCommand {

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    if (line(0).equals(' ')) {
      var newLine = line.substring(1)
      if (line(0).equals(' ')) newLine = newLine.substring(1)
      val newLines = lines.updated(state.position.line, newLine)
      val content = newLines.mkString("\n")
      new State(content, State.Position(state.position.line, state.position.character-2), state.selection, true)
    }
    else state
  }
}
