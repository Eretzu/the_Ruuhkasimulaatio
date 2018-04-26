package ryysissimulaatio

class Human( x: Double, y: Double, val room: Room, val radius: Int = 8, val mass: Double = 1) {
  require(radius >= 2)
  require(mass > 0)
  
  private var _position = Vector2D(x, y)
  private var _velocity = Vector2D(0,0)
  private var _acceleration = Vector2D(0,0)
  
  private val steering = new SteeringAlgorithm(this, room)
  
  def position = _position
  def velocity = _velocity
  def acceleration = _acceleration
  def heading = _velocity.normalize()
  
  private def getAcceleration(): Vector2D = {
    _acceleration = steering.getAcceleration().truncate(Human.MaxForce*Human.accelerationMultiplier)/(mass*Human.massMultiplier)
    _acceleration
  }
  
  def move() = {
    // Get acceleration limited by MaxForce
    _velocity += getAcceleration().truncate(Human.MaxForce)
    // Truncate velocity to MaxSpeed
    _velocity = velocity.truncate(Human.MaxSpeed*Human.speedMultiplier)
    _position += velocity
  }
}

object Human {
  val MaxSpeed: Double = 0.7
  val MaxForce: Double = 0.1
  
  private var _speedMultiplier = 1.0
  private var _accelerationMultiplier = 1.0
  private var _massMultiplier = 1.0
  
  def speedMultiplier = _speedMultiplier
  def speedMultiplier_=(value: Double) = _speedMultiplier = value  
  def accelerationMultiplier = _accelerationMultiplier
  def accelerationMultiplier_=(value: Double) = _accelerationMultiplier = value  
  def massMultiplier = _massMultiplier
  def massMultiplier_=(value: Double) = _massMultiplier = value
}