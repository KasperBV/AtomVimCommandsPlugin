package viw.internals

import org.scalatest.FunSuite
import viw.ViwTest

class StateTest extends FunSuite with ViwTest {

  test("State from cursoredText should be equal to its source") {
    val sourceText =
      """Lore#m# ipsum dolor sit amet, consectetur adipiscing elit.
        |Donec eu mi commodo, consequat purus vitae, facilisis nunc.
      """.stripMargin

    val state = State.fromCursoredText(sourceText)

    assert(state.cursoredText === sourceText.replaceAll("\r", ""))
    assert(state.position.character === 4)
    assert(state.position.line === 0)
  }

  test(
    "State from cursoredText should be equal to its source with an empty cursor") {
    val sourceText =
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n##"

    val state = State.fromCursoredText(sourceText)

    assert(state.cursoredText === sourceText)
    assert(state.position.character === 0)
    assert(state.position.line === 1)
  }

  test("ProperSplit preserves newlines") {
    assert(State.properSplit("a\n") === Vector("a", ""))
  }

  test("State next position") {
    val sourceText =
      """Lore#m# ipsum dolor sit amet, consectetur adipiscing elit.
        |Donec eu mi commodo, consequat purus vitae, facilisis nunc.
      """.stripMargin
    val state = State.fromCursoredText(sourceText)
    val nextPosition = State.getPositionAfter(state.contentLines, state.position)
    assert(nextPosition.get.character === 5)
    assert(nextPosition.get.line === 0)
  }

  test("State next position after end of line") {
    val sourceText =
      """Lore#m#
        |Donec eu mi commodo, consequat purus vitae, facilisis nunc.
      """.stripMargin
    val state = State.fromCursoredText(sourceText)
    val nextPosition = State.getPositionAfter(state.contentLines, state.position)
    assert(nextPosition.nonEmpty)
    assert(nextPosition.get.character === 0)
    assert(nextPosition.get.line === 1)
  }

  test("State next position after end of text") {
    val sourceText =
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Donec eu mi commodo, consequat purus vitae, facilisis nunc#.#""".stripMargin
    val state = State.fromCursoredText(sourceText)
    val nextPosition = State.getPositionAfter(state.contentLines, state.position)
    assert(nextPosition.isEmpty)
  }

  test("State previous position") {
    val sourceText =
      """Lore#m# ipsum dolor sit amet, consectetur adipiscing elit.
        |Donec eu mi commodo, consequat purus vitae, facilisis nunc.
      """.stripMargin
    val state = State.fromCursoredText(sourceText)
    val previousPosition = State.getPositionBefore(state.contentLines, state.position)
    assert(previousPosition.get.character === 3)
    assert(previousPosition.get.line === 0)
  }

  test("State previous position after start of line") {
    val sourceText =
      """Lorem
        |#D#onec eu mi commodo, consequat purus vitae, facilisis nunc.
      """.stripMargin
    val state = State.fromCursoredText(sourceText)
    val previousPosition = State.getPositionBefore(state.contentLines, state.position)
    assert(previousPosition.nonEmpty)
    assert(previousPosition.get.character === 4)
    assert(previousPosition.get.line === 0)
  }

  test("State previous position after start of text") {
    val sourceText =
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Donec eu mi commodo, consequat purus vitae, facilisis nunc.""".stripMargin
    val state = State.fromCursoredText(sourceText)
    val previousPosition = State.getPositionBefore(state.contentLines, state.position)
    assert(previousPosition.isEmpty)
  }

}