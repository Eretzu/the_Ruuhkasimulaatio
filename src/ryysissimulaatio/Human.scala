package ryysissimulaatio

class Human( x: Double, y: Double, val room: Room) {
  val mass = 1
  var position = Vector2D(x, y)
  var speed = Vector2D(0,0)
  var heading = 0
  
  private def getAcceleration() = SteeringAlgorithm.getAcceleration(this, room)
  
  def move() = {
    speed += getAcceleration()
    position += speed
  }
}

object Human {
  val MaxForce = 1
  val MaxSpeed = 1
}