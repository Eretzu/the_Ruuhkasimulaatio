import scala.swing._

class UI( val width: Int, val height: Int, val humans: Int ) extends MainFrame {
  title = "the Ryysissimulaatio"
  preferredSize = new Dimension(320, 240)
  contents = new BoxPanel(Orientation.Vertical) {
    contents += new Label("Here is the contents!")
    contents += new Label(width.toString)
    contents += new Label(height.toString)
    contents += new Label(humans.toString)
  }
}

object TheRuuhkasimulaatio {
  def main(args: Array[String]) {
    val vals = start()
    val ui = new UI(vals._1, vals._2, vals._3)
    ui.visible = true
    println("End of main function")
  }
  
  def start() : (Int, Int, Int) = {
    var width = "100"
    var height = "100"
    var humans = "5"
    var r = Dialog.showInput(null, "Room width", initial=width)
    r match {
      case Some(s) => width = s
      case None =>
    }
    r = Dialog.showInput(null, "Room height", initial=height)
    r match {
      case Some(s) => height = s
      case None =>
    }
    r = Dialog.showInput(null, "Number of humans", initial=humans)
    r match {
      case Some(s) => humans = s
      case None =>
    }
    return (width.toInt, height.toInt, humans.toInt)
  }
}