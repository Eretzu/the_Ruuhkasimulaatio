package ryysissimulaatio

class Human( x: Double, y: Double, val room: Room) {
  val mass = 1
  private var _position = Vector2D(x, y)
  private var _velocity = Vector2D(0,0)
  private val steering = new SteeringAlgorithm(this, room)
  //private var heading = 0
  
  def position = _position
  def velocity = _velocity
  
  private def getAcceleration() = steering.getAcceleration().derive(0, Human.MaxForce)
  
  def move() = {
    _velocity += getAcceleration()
    _velocity = velocity.truncate(Human.MaxSpeed)
    _position += velocity
  }
}

object Human {
  val MaxForce = 1
  val MaxSpeed = 3
}