package viw

import org.scalatest.{BeforeAndAfter, FunSuite}

class CommandsTestAdvanced extends FunSuite with ViwTest with BeforeAndAfter {
  before {
    // setup your test
  }

  after {
    // cleanup your history
  }

  val sourceText =
    """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
      |Cras quis massa eu ex commodo imperdiet.
      |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin



  test("Moving left at the start of line") {
    viwTrue(
      "h",
      sourceText,
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin
    )
  }


  test("Moving right at the end of line2") {
    viwTrue(
      "lllllllllllllllllllll",
      """Lorem ipsum dolor sit amet, consectetur adipiscing #e#lit.""",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit#.#"""
    )
  }

  test("Moving up at first line") {
    viwTrue(
      "kkk",
      sourceText,
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin
    )
  }

  test("Moving down at the last line") {
    viwTrue(
      "jjjj",
      sourceText,
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |#C#urabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin
    )
  }

  test("Next word, at end of line") {
    viwTrue(
      "w",
      """Lorem ipsum dolor sit amet, consectetur adipiscing #e#lit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin,
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |#C#ras quis massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Next word multiple spaces") {
    viwTrue(
      "www",
      """Lorem ip#s#um  dolor   sit    amet, consectetur adipiscing elit.""",
      """Lorem ipsum  dolor   sit    #a#met, consectetur adipiscing elit.""")
  }

  test("Back word multiple spaces") {
    viwTrue(
      "bbb",
      """Lorem ipsum  dolor   sit     a#m#et, consectetur adipiscing elit.""",
      """Lorem ipsum  #d#olor   sit     amet, consectetur adipiscing elit.""")
  }


  test("Next word last word") {
    viwTrue(
      "w",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo #i#mperdiet.""".stripMargin,
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo #i#mperdiet.""".stripMargin
    )
  }

  test("Next word last letter") {
    viwTrue(
      "w",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet#.#""".stripMargin,
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet#.#""".stripMargin
    )
  }

  test("Next word middle of word") {
    viwTrue(
      "w",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex co#m#modo imperdiet.""".stripMargin,
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo #i#mperdiet.""".stripMargin
    )
  }

  test("Back word, middle of word") {
    viwTrue(
      "b",
      """Lor#e#m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin,
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Back word, at end of line") {
    viwTrue(
      "b",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |#C#ras quis massa eu ex commodo imperdiet.""".stripMargin,
      """Lorem ipsum dolor sit amet, consectetur adipiscing #e#lit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Back word, at end of line, middle of the word") {
    viwTrue(
      "b",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cr#a#s quis massa eu ex commodo imperdiet.""".stripMargin,
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |#C#ras quis massa eu ex commodo imperdiet.""".stripMargin
    )
  }


  test("Back word, first word") {
    viwTrue(
      "b",
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin,
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("End of word, last word") {
    viwTrue(
      "e",
      """Lorem ipsum dolor sit amet, consectetur adipiscing #e#lit.""",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit#.#"""
    )
  }

  test("next Bracket line") {
    viwTrue(
      "%",
      """Lorem ip[#s#um dolor sit amet, consectetur adipiscing ]elit.""",
      """Lorem ip#[#sum dolor sit amet, consectetur adipiscing ]elit."""
    )
  }

  test("next Bracket line no bracket") {
    viwTrue(
      "%",
      """Lorem ip#s#um dolor sit amet, consectetur adipiscing elit.""",
      """Lorem ip#s#um dolor sit amet, consectetur adipiscing elit."""
    )
  }

  test("Brackets Forward") {
    viwTrue(
      "%",
      """Lorem ip#[#sum dolor sit amet, consectetur adipiscing ]elit.""",
      """Lorem ip[sum dolor sit amet, consectetur adipiscing #]#elit."""
    )
  }

  test("Bracket Forward 2 lines") {
    viwTrue(
      "%",
      """Lore#(#m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras qu)is massa eu ex commodo imperdiet.""".stripMargin,
      """Lore(m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras qu#)#is massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Bracket Forward 2 lines nested") {
    viwTrue(
      "%",
      """Lore#(#m ipsum d(olor sit amet, consectetur adipiscing elit.
        |Cras) qu)is massa eu ex commodo imperdiet.""".stripMargin,
      """Lore(m ipsum d(olor sit amet, consectetur adipiscing elit.
        |Cras) qu#)#is massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Bracket Forward no match") {
    viwTrue(
      "%",
      """Lore#(#m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin,
      """Lore#(#m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Bracket Forward First and last") {
    viwTrue(
      "%",
      """#(#Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.)""".stripMargin,
      """(Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.#)#""".stripMargin
    )
  }

  test("Bracket start to end") {
    viwTrue(
      "%",
      """#(#Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.)""".stripMargin,
      """(Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.#)#""".stripMargin
    )
  }

  test("Brackets backwards") {
    viwTrue(
      "%",
      """Lorem ip[sum dolor sit amet, consectetur adipiscing #]#elit.""",
      """Lorem ip#[#sum dolor sit amet, consectetur adipiscing ]elit."""
    )
  }

  test("Bracket Backward 2 lines") {
    viwTrue(
      "%",
      """Lore(m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras qu#)#is massa eu ex commodo imperdiet.""".stripMargin,
      """Lore#(#m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras qu)is massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Bracket Backward 2 lines nested") {
    viwTrue(
      "%",
      """Lore(m ipsum d(olor sit amet, consectetur adipiscing elit.
        |Cras) qu#)#is massa eu ex commodo imperdiet.""".stripMargin,
      """Lore#(#m ipsum d(olor sit amet, consectetur adipiscing elit.
        |Cras) qu)is massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Bracket Backward First and last") {
    viwTrue(
      "%",
      """(Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.#)#""".stripMargin,
      """#(#Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.)""".stripMargin
    )
  }

  test("Bracket Backward no match") {
    viwTrue(
      "%",
      """Lore#)#m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin,
      """Lore#)#m ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.""".stripMargin
    )
  }

  test("Move Count double digits") {
    viwTrue(
      "10h",
      """Lorem ip[sum dolo#r# sit amet, consectetur adipiscing ]elit.""",
      """Lorem i#p#[sum dolor sit amet, consectetur adipiscing ]elit."""
    )
  }


  test("Delete line first character") {
    viwTrue(
      "D",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |#C#ras quis massa eu ex commodo imperdiet.
        |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin,
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |#C#urabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin,
    )
  }

  test("Delete first line first character") {
    viwTrue(
      "D",
      """#(#Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.)""".stripMargin,
      """#C#ras quis massa eu ex commodo imperdiet.)"""
    )
  }


  test("Delete line only line") {
    viwTrue(
      "D",
      """#(#Lorem ipsum dolor sit amet, consectetur adipiscing elit.""",
      """##"""
    )
  }



  test("Delete Move end of line") {
    viwTrue(
      "d$",
      """Lorem ip#[#sum dolor sit amet, consectetur adipiscing ]elit.""",
      """Lorem ip#.#"""
    )
  }

  test("Delete j entire line") {
    viwTrue(
    "dj",
      """#A#BCDEFG HIJKLMNOP
      |XRST UVWXYZ""".stripMargin,
      """#X#RST UVWXYZ""",
    )
  }

  test("Delete k entire line") {
    viwTrue(
      "dk",
      """ABCDEFG HIJKLMNOP
        |XRST UVWXY#Z#""".stripMargin,
      """ABCDEFG HI#J#""",
    )
  }

  test("Delete j multiple lines") {
    viwTrue(
      "d3j",
      """#A#BCDEFG HIJKLMNOP
        |XRST UVWXYZ
        |ADGEFDD  GSDFSDFEFE
        |DFDCSDFDFDSFDSF
        |DSFSDFDSFDSFDSFDSf""".stripMargin,
      """#D#FDCSDFDFDSFDSF
        |DSFSDFDSFDSFDSFDSf""".stripMargin,
    )
  }

  test("Delete k multiple lines") {
    viwTrue(
      "d3k",
      """ABCDEFG HIJKLMNOP
        |XRST UVWXYZ
        |ADGEFDD  GSDFSDFEFE
        |DFDCS#F#""".stripMargin,
      """ABCDE#F#""".stripMargin,
    )
  }

  test("Delete Move Count") {
    viwTrue(
      "d5h",
      """Lorem ip#[#sum dolor sit amet, consectetur adipiscing ]elit.""",
      """Lor#e#sum dolor sit amet, consectetur adipiscing ]elit."""
    )
  }

  test("Delete Move Count double digits") {
    viwTrue(
      "d10h",
      """Lorem ip[sum dolo#r# sit amet, consectetur adipiscing ]elit.""",
      """Lorem i#p# sit amet, consectetur adipiscing ]elit."""
    )
  }

  test("Delete MoveBrackets") {
    viwTrue(
      "d%",
      """#(#Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.)""".stripMargin,
      """#)#"""
    )
  }

  test("repeat") {
    viwTrue(
      "b..",
      """Lorem ipsum  dolor   sit     a#m#et, consectetur adipiscing elit.""",
      """Lorem ipsum  #d#olor   sit     amet, consectetur adipiscing elit.""")
  }

  test("Bind and use new command") {

    viwTrue(
      "hBgg",
      """Lorem ipsum  dol#o#r   sit     amet, consectetur adipiscing elit.""",
      """Lorem ipsum  d#o#lor   sit     amet, consectetur adipiscing elit.""")
    }

  test("Bind and use new command twice") {
    viwTrue(
      "hBggg",
      """Lorem ipsum  dol#o#r   sit     amet, consectetur adipiscing elit.""",
      """Lorem ipsum  #d#olor   sit     amet, consectetur adipiscing elit.""")
    }

  test("Bind and use new command countCommand") {
    viwTrue(
      "5hBgg",
      """Lorem ipsum  dolo#r#   sit     amet, consectetur adipiscing elit.""",
      """Lorem i#p#sum  dolor   sit     amet, consectetur adipiscing elit.""")
  }

  test("Chain command") {
    viwTrue(
      "5hz5lh",
      """Lorem ipsum  dol#o#r   sit     amet, consectetur adipiscing elit.""",
      """Lorem ipsu#m#  dolor   sit     amet, consectetur adipiscing elit.""")
  }

  test("Bind and clear") {
    viwTrue(
      "5hBggBdggh",
      """Lorem ipsum  dolo#r#   sit     amet, consectetur adipiscing elit.""",
      """Lorem #i#psum  dolor   sit     amet, consectetur adipiscing elit.""")
  }

  test( "Yank and paste single line") {

    viwTrue(
      "y3lp",
      """Lorem ip#s#um dolor sit amet, consectetur adipiscing elit.""",
      """Lorem ip#s#sumum dolor sit amet, consectetur adipiscing elit."""
    )

  }

  test( "Yank and paste single line back") {

    viwTrue(
      "y3hp",
      """Lorem ip#s#um dolor sit amet, consectetur adipiscing elit.""",
      """Lorem ip#s#ipsum dolor sit amet, consectetur adipiscing elit."""
    )

  }

  test( "Yank and paste multiple lines") {
    viwTrue("y2jp",
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin,
      """#L#Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin
    )
  }

  test( "Yank and paste multiple lines back") {
    viwTrue("y2kp",
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |#C#urabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin,
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |#C#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin
    )
  }

  test( "Delete and paste single line") {

    viwTrue(
      "d3lP",
      """Lorem ip#s#um dolor sit amet, consectetur adipiscing elit.""",
      """Lorem ip#s#um dolor sit amet, consectetur adipiscing elit."""
    )

  }

  test( "Delete and paste single line back") {

    viwTrue(
      "d3hp",
      """Lorem ip#s#um dolor sit amet, consectetur adipiscing elit.""",
      """Lorem# #ipsum dolor sit amet, consectetur adipiscing elit."""
    )

  }

  test ( "Delete and paste MultipleLines"){
    viwTrue("d2jP",
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin,
      """#L#orem ipsum dolor sit amet, consectetur adipiscing elit.
        |Cras quis massa eu ex commodo imperdiet.
        |Curabitur auctor tellus at justo malesuada, at ornare mi tincidunt.""".stripMargin.stripMargin
    )
  }



}
