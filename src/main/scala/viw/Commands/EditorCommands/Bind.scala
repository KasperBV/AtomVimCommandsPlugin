package viw.Commands.EditorCommands
import viw.Commands.Command
import viw.EditorState

class Bind(k: Char) extends EditorCommand {

  val key: Char = k

  override def process(eState: EditorState): EditorState = {
    val bindings = key match {
      case 'd'=> Map[Char, Command]()
      case _ => eState.bindings + (key -> eState.lastCommand.get)
    }
    new EditorState(eState.lastCommand, eState.viewMode, bindings, eState.copyBuffer)
  }

  override def equals(x: Any): Boolean ={
    x match {
      case b: Bind => key == b.key
      case _ => false
    }
  }

}
