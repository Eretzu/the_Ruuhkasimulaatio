package ryysissimulaatio

import java.awt.{Graphics2D,Color,BasicStroke}
import java.awt.event.ActionListener
import java.awt.geom._
import scala.swing._
import scala.math._
import event._

object Vars {
  val Marginal = 10
  val WallSize = 16
  val Border = Marginal+WallSize
}

class UI(room: Room) extends Frame {
  val canvas = new Canvas(room)
  
  title = "the Ryysissimulaatio"
  preferredSize = new Dimension(room.width+Vars.Border*2, 
                                room.height+Vars.Border*2)
  resizable = false
  contents = new BoxPanel(Orientation.Vertical) {
    contents += canvas
  }
  
  def tick() = {
    room.runRound()
    canvas.repaint()
  }
}

// Canvas where room and people are drawn and repainted every tick.
class Canvas(val room: Room) extends Component {
  override def paintComponent(g : Graphics2D) {
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
		                   java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    
    // Draw white background
		g.setColor(Color.white)
    g.fillRect(0,0, Vars.Border*2+room.width, Vars.Border*2+room.height)
    
    // Draw walls, we use fillRect as it gives better control than drawRect
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

	  // Draw humans and velocity vectors 
	  for (human <- room.humans) {
	    val x = Vars.Border + human.position.x
	    val y = Vars.Border + human.position.y
	    val lineLength = 40
	    g.setStroke(new BasicStroke())
	    g.setColor(Color.black)
	    g.draw(new Line2D.Double(x+human.heading.x*human.radius, y+human.heading.y*human.radius, x+human.velocity.x*lineLength, y+human.velocity.y*lineLength))
	    g.setColor(Color.blue)
      g.fill(new Ellipse2D.Double(x-human.radius, y-human.radius, human.radius*2, human.radius*2))
    }
  }
}

// MainFrame, program closes when this is closed
class OptionsPanel(vals: (Int, Int, Int)) extends MainFrame {
  private var room = new Room( vals._1, vals._2, vals._3)
  private var ui = new UI(room)
  ui.visible = true
    
  val listener = new ActionListener(){
    def actionPerformed(e : java.awt.event.ActionEvent) = {
      ui.tick()
    }  
  }
  
  val timer = new javax.swing.Timer(17, listener) // Timer is started with "Start" -button  
  
  val speedSlider = new Slider {
    min = 50
    value = 100
    max = 200
    labels = Map((50, new Label("50%")), (100, new Label("100%")), (150, new Label("Speed")), (200, new Label("200%")))
    paintLabels = true
  }
  val accelerationSlider = new Slider {
    min = 50
    value = 100
    max = 200
    labels = Map((50, new Label("50%")), (100, new Label("100%")), (150, new Label("Acceleration")), (200, new Label("200%")))
    paintLabels = true
  }
  val massSlider = new Slider {
    min = 50
    value = 100
    max = 200
    labels = Map((50, new Label("50%")), (100, new Label("100%")), (150, new Label("Mass")), (200, new Label("200%")))
    paintLabels = true
  }
  
  val wanderBox = new CheckBox("Wandering") { selected = true }
  val seekBox = new CheckBox("Seeking") { selected = true }
  val wallAvoidanceBox = new CheckBox("Wall Avoidance") { selected = true }
  val separationBox = new CheckBox("Separation") { selected = true }
  
  val timerButton = new Button("Start") 
  val restartButton = new Button("Restart")
  val exitButton = new Button("Quit")
  
  contents = new BoxPanel(Orientation.Vertical) {
    contents += speedSlider
    contents += accelerationSlider
    contents += massSlider
    contents += wanderBox
    contents += seekBox
    contents += wallAvoidanceBox
    contents += separationBox
    contents += timerButton
    contents += restartButton
    contents += exitButton
  }
  
  listenTo(speedSlider, accelerationSlider, massSlider)
  listenTo(wanderBox, seekBox, wallAvoidanceBox, separationBox)
  listenTo(timerButton, restartButton, exitButton)
  
  reactions += {
    case ButtonClicked(`wanderBox`) => 
      room.toggleWander()
    case ButtonClicked(`seekBox`) => 
      room.toggleSeek()
    case ButtonClicked(`wallAvoidanceBox`) => 
      room.toggleWallAvoidance()
    case ButtonClicked(`separationBox`) => 
      room.toggleSeparation()
    case ButtonClicked(`timerButton`) => {
      println(timerButton.text)
      if(!timer.isRunning()) {
        timer.start()
        timerButton.text = "Stop"
      }
      else {
        timer.stop()
        timerButton.text = "Start"
      }
    }
    case ButtonClicked(`restartButton`) =>
      restart()
    case ButtonClicked(`exitButton`) =>
      System.exit(0)
    case ValueChanged(`speedSlider`) => 
      Human.speedMultiplier = speedSlider.value.toDouble/100
    case ValueChanged(`accelerationSlider`) => 
      Human.accelerationMultiplier = accelerationSlider.value.toDouble/100
    case ValueChanged(`massSlider`) => 
      Human.massMultiplier = massSlider.value.toDouble/100
  }
  
  this.pack()
  minimumSize = this.size
  preferredSize = this.size
  
  def restart() = {
    timer.stop()
    ui.close
    speedSlider.value = 100
    accelerationSlider.value = 100
    massSlider.value = 100
    wanderBox.selected = true
    seekBox.selected = true
    wallAvoidanceBox.selected = true
    separationBox.selected = true
    timerButton.text = "Start"
    room = new Room( vals._1, vals._2, vals._3)
    ui = new UI(room)
    ui.visible = true
    //timer.start()
  }
}

// Object that contains main and allows us to start the program. Setup gives us values for room width, height and population.
object TheRuuhkasimulaatio {  
  def main(args: Array[String]) {
    val vals = setup()
    
    val options = new OptionsPanel(vals)
    options.visible = true

    println("End of main function")
  }
  
  def setup() : (Int, Int, Int) = {
    var width = "500"
    var height = "500"
    var humans = "50"
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
    return (max(width.toInt, 200), max(height.toInt, 200), max(humans.toInt,1))
  }
}