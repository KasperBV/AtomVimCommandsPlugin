package viw.Commands.StateCommands
import viw.internals.State

 class Paste(cBuffer: String) extends StateCommand {

   val copyBuffer: String = cBuffer

   override def process(state: State): State = {
     val lines = State.properSplit(state.content)
     val line = lines(state.position.line)
     val newLine = line.substring(0, state.position.character+1) + copyBuffer + line.substring(state.position.character + 1)
     val newLines = lines.updated(state.position.line, newLine).mkString("\n")
     new State(newLines, state.position, state.selection, true)
   }

   override def equals(x: Any): Boolean ={
     x match {
       case p: Paste => copyBuffer == p.copyBuffer
       case _ => false
     }
   }

}
