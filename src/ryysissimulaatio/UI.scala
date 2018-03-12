package ryysissimulaatio

import java.awt.{Graphics2D,Color,BasicStroke}
import java.awt.event.ActionListener
import java.awt.geom._
import scala.swing._
import scala.math._

object Vars {
  val Marginal = 10
  val WallSize = 16
  val Border = Marginal+WallSize
}

class UI( val width: Int, val height: Int, val humans: Int ) extends MainFrame {
  val room = new Room(width, height, humans)
  val canvas = new Canvas(room)
  
  title = "the Ryysissimulaatio"
  preferredSize = new Dimension(width+Vars.Border*2, 
                                height+Vars.Border*2)
  resizable = false
  contents = new BoxPanel(Orientation.Vertical) {
    contents += canvas
  }
  
  def tick() = {
    room.runRound()
    canvas.repaint()
  }
}

class Canvas(val room: Room) extends Component {

  override def paintComponent(g : Graphics2D) {
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
		                   java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    
    // Draw white background
		g.setColor(Color.white)
    g.fillRect(0,0, Vars.Border*2+room.width, Vars.Border*2+room.height)
    
    // Draw walls, we use fillRect as it gives better control than drawRect+stroke
    g.setColor(Color.black)
	  g.fillRect(Vars.Marginal, Vars.Marginal, room.width+Vars.WallSize*2, room.height+Vars.WallSize*2)
	  
	  // Draw the actual room
	  g.setColor(Color.white)
	  g.fillRect(Vars.Border, Vars.Border, room.width, room.height)
	  
	  // Draw door
	  g.setColor(Color.white)
	  val doorX = Vars.Border + room.door.x
	  val doorY = Vars.Border + room.door.y
	  g.fillRect(doorX.toInt-Vars.WallSize, doorY.toInt-room.doorWidth/2, Vars.WallSize, room.doorWidth)

	  // Draw humans
	  for (human <- room.humans) {
	    val x = Vars.Border + human.position.x
	    val y = Vars.Border + human.position.y
	    g.setColor(Color.blue)
      g.fill(new Ellipse2D.Double(x-human.radius, y-human.radius, human.radius*2, human.radius*2))
    }
  }
}

object TheRuuhkasimulaatio {
  def main(args: Array[String]) {
    val vals = setup()
    val ui = new UI(vals._1, vals._2, vals._3)
    ui.visible = true
    
    val listener = new ActionListener(){
      def actionPerformed(e : java.awt.event.ActionEvent) = {
        ui.tick()
      }  
    }
    
    val timer = new javax.swing.Timer(6, listener)
    timer.start()

    println("End of main function")
  }
  
  def setup() : (Int, Int, Int) = {
    var width = "500"
    var height = "500"
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
    return (max(width.toInt, 100), max(height.toInt, 100), max(humans.toInt,1))
  }
}