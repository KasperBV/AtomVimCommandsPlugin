package viw.Commands.StateCommands
import viw.internals.State

class PasteBehind(cBuffer: String) extends StateCommand {

  val copyBuffer: String = cBuffer

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    val firstLines = lines.slice(0, state.position.line).mkString("\n")
    val lastLines = lines.slice(state.position.line, lines.length).mkString("\n")
    val newLines = firstLines + lastLines.substring(0, state.position.character) + copyBuffer + lastLines.substring(state.position.character)
    new State(newLines, state.position, state.selection, true)
  }

  override def equals(x: Any): Boolean ={
    x match {
      case p: PasteBehind => copyBuffer == p.copyBuffer
      case _ => false
    }
  }

}
