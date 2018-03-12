package ryysissimulaatio

import java.awt.{Graphics2D,Color,BasicStroke}
import java.awt.event.ActionListener
import java.awt.geom._
import scala.swing._
import scala.math._

object Helper {
  val scale = 20
  val Marginal = (0.3*scale).toInt
  val WallSize = (0.5*scale).toInt
  val EdgeSize = Marginal+WallSize
  
  // converts meters to pixels
  def convert(meters: Double): Int = round((meters*scale)).toInt
}

class UI( val width: Int, val height: Int, val humans: Int ) extends MainFrame {
  val room = new Room(width, height, humans)
  val canvas = new Canvas(room)
  
  title = "the Ryysissimulaatio"
  
  // Start with one meter being 100 pixels
  preferredSize = new Dimension(Helper.EdgeSize*2+Helper.convert(width), 
                                Helper.EdgeSize*2+Helper.convert(height))
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
    g.fillRect(0,0, Helper.EdgeSize*2+Helper.convert(room.width), Helper.EdgeSize*2+Helper.convert(room.height))
    
    // Draw walls, we use fillRect as it gives better control than drawRect+stroke
    g.setColor(Color.black)
	  g.fillRect(Helper.Marginal, Helper.Marginal, Helper.convert(room.width)+Helper.WallSize*2, Helper.convert(room.height)+Helper.WallSize*2)
	  
	  // Draw the actual room
	  g.setColor(Color.white)
	  g.fillRect(Helper.EdgeSize, Helper.EdgeSize, Helper.convert(room.width), Helper.convert(room.height))
	  
	  // Draw door
	  g.setColor(Color.white)
	  val doorX = Helper.EdgeSize+Helper.convert(room.door.x)
	  val doorY = Helper.EdgeSize+Helper.convert(room.door.y)
	  g.fillRect(doorX-Helper.WallSize, doorY-Helper.convert(room.doorWidth/2), Helper.WallSize, Helper.convert(room.doorWidth))

	  // Draw humans
	  for (human <- room.humans) {
	    val x = Helper.EdgeSize+Helper.convert(human.position.x)
	    val y = Helper.EdgeSize+Helper.convert(human.position.y)
	    g.setColor(Color.blue)
      g.fill(new Ellipse2D.Double(x-Helper.convert(Human.Radius), y-Helper.convert(Human.Radius), Helper.convert(Human.Radius)*2, Helper.convert(Human.Radius)*2))
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
    
    val timer = new javax.swing.Timer(17, listener)
    timer.start()

    println("End of main function")
  }
  
  def setup() : (Int, Int, Int) = {
    var width = "50"
    var height = "50"
    var humans = "10"
    var r = Dialog.showInput(null, "Room width in meters", initial=width)
    r match {
      case Some(s) => width = s
      case None =>
    }
    r = Dialog.showInput(null, "Room height in meters", initial=height)
    r match {
      case Some(s) => height = s
      case None =>
    }
    r = Dialog.showInput(null, "Number of humans", initial=humans)
    r match {
      case Some(s) => humans = s
      case None =>
    }
    return (max(width.toInt, 10), max(height.toInt, 10), max(humans.toInt, 1))
  }
}