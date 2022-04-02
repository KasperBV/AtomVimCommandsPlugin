package viw
import viw.Commands.Command
import viw.Commands.NotBound
import viw.internals.State



object Viw {

  private var editorState = EditorState(None, false, Map[Char, Command](), "")
  private var inputBuffer = Vector[String]()

  // Returns the proper new State based on the input key and the state of the Object variables.
  // Returns None if no valid command can be found or if the command has no effect
  // Updates the Object variables of the class.
  def processKey(key: String, state: State): Option[State] = {
    if (state.mode)  {
      inputBuffer = inputBuffer ++ Vector(key)
      CommandFactory.produceCommand(inputBuffer, editorState) match {
        case None => None
        case Some(c) => processCommand(state, c)
      }
    }
    else if (key == " ") Some(state.copy(mode = true))
    else None
  }

  def processCommand(state: State, command: Command): Option[State] = {
    command match {
          case NotBound =>
            inputBuffer = Vector[String] ()
            None
          case _ =>
            inputBuffer = Vector[String] ()
            val newStates = command.process (state, editorState)
            editorState = EditorState(Some(command), newStates._2.viewMode, newStates._2.bindings, newStates._2.copyBuffer)
            Some (newStates._1)
    }
  }

}

case class EditorState(lastCommand: Option[Command], viewMode: Boolean, bindings: Map[Char, Command], copyBuffer: String)


