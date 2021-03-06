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
  
  // Uses the steering algorithm to get acceleration.
  private def getAcceleration(): Vector2D = {
    _acceleration = steering.getAcceleration().truncate(Human.MaxForce*Human.accelerationMultiplier)/(mass*Human.massMultiplier)
    _acceleration
  }
  
  // Acceleration -> velocity -> position
  def move() = {
    _velocity += getAcceleration().truncate(Human.MaxForce) // Get acceleration limited by MaxForce
    _velocity = velocity.truncate(Human.MaxSpeed*Human.speedMultiplier) // Truncate velocity to MaxSpeed
    
    val oldPosition = _position // for collision detection
    _position += velocity
    
    // Collision detection with walls
    if(oldPosition.x < 0 || !((room.door - _position).length <= room.doorWidth/2)) {
      if(oldPosition.x > 0 && _position.x < 0) _position = Vector2D(oldPosition.x, _position.y)
      else if(oldPosition.x < room.width && _position.x > room.width) _position = Vector2D(oldPosition.x, _position.y)
      

      if(oldPosition.y > 0 && _position.y < 0) _position = Vector2D(_position.x, oldPosition.y)
      else if(oldPosition.y < room.width && _position.y > room.height) _position = Vector2D(_position.x, oldPosition.y)
    }
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