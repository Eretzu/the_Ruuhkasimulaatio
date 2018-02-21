package ryysissimulaatio

import java.awt.{Graphics2D,Color,BasicStroke}
import java.awt.geom._
import scala.swing._

class UI( val width: Int, val height: Int, val humans: Int ) extends MainFrame {
  val Marginal = 15
  val room = new Room(width, height, humans)
  val canvas = new Canvas(room, Marginal)
  
  title = "the Ryysissimulaatio"
  preferredSize = new Dimension(width+Marginal*2, height+Marginal*2)
  resizable = false
  contents = new BoxPanel(Orientation.Vertical) {
    //contents += new Label("Here is the contents!")
    /*contents += new Label(width.toString)
    contents += new Label(height.toString)
    contents += new Label(humans.toString)*/
    contents += canvas
  }
  
  def tick() = {
    room.humans.foreach( ??? )
    canvas.repaint()
  }
}

class Canvas(val room: Room, val Marginal: Int) extends Component {

  override def paintComponent(g : Graphics2D) {
    val d = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
		                   java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(Color.white)
    g.fillRect(0,0, d.width, d.height)
    g.setColor(Color.black)
	  g.setStroke(new BasicStroke(10))
	  g.drawRect(Marginal-5,Marginal-5, d.width-(Marginal-5)*2, d.height-(Marginal-5)*2)
	  for (human <- room.humans) {
	    val x = human._1+Marginal
	    val y = human._2+Marginal
	    g.setColor(Color.blue)
      g.fill(new Ellipse2D.Double(x, y, 15, 15))
    }
  }
}

object TheRuuhkasimulaatio {
  def main(args: Array[String]) {
    val vals = setup()
    val ui = new UI(vals._1, vals._2, vals._3)
    ui.visible = true
    
    val timer = new javax.swing.Timer(6, ui.tick())
    timer.start()

    println("End of main function")
  }
  
  def setup() : (Int, Int, Int) = {
    var width = "800"
    var height = "800"
    var humans = "10"
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