import scala.swing._

class UI extends MainFrame {
  title = "the Ryysissimulaatio"
  preferredSize = new Dimension(320, 240)
  contents = new Label("Here is the contents!")
}

object TheRuuhkasimulaatio {
  def main(args: Array[String]) {
    val ui = new UI
    ui.visible = true
    println("End of main function")
  }
}