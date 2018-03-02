package ryysissimulaatio

class Human( x: Double, y: Double, val room: Room) {
  val mass = 1
  var position = Vector2D(x, y)
  var speed = Vector2D(0,0)
  var heading = 0
  
  def getAcceleration() = SteeringAlgorithm.getAcceleration(this, room)
  
  def move() = {
    position += Vector2D(1,0)
  }
}

object Human {
  val MaxForce = 1
  val MaxSpeed = 1
}