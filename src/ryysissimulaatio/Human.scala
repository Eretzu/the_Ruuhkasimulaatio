package ryysissimulaatio

class Human( x: Double, y: Double, val room: Room, val mass: Double = 1) {
  require(mass > 0)
  
  private var _position = Vector2D(x, y)
  private var _velocity = Vector2D(0, 0)
  private val steering = new SteeringAlgorithm(this, room)
  //private var heading = 0
  
  def position = _position
  def velocity = _velocity
  
  private def getAcceleration(): Vector2D = steering.getAcceleration().truncate(Human.MaxForce)/mass
  
  def move() = {
    // Get acceleration limited by MaxForce
    _velocity += getAcceleration().truncate(Human.MaxForce)
    // Truncate velocity to MaxSpeed
    _velocity = velocity.truncate(Human.MaxSpeed)
    _position += velocity
    
    /*
    if(_position.x < 7 && (_position.y < room.door-25 || _position.y > room.door+25)) _position = Vector2D(7, _position.y)
    else if(room.width - _position.x < 7) _position = Vector2D(room.width-7, _position.y)
    if(_position.y < 7) _position = Vector2D(_position.x, 7)
    else if(room.height - _position.y < 7) _position = Vector2D(_position.x, room.height-7)
    */
    
    //if(_position.x < -5) room.delete(this)
  }
}

object Human {
  // According to wikipedia average human speed is something like 1.4 m/s
  val MaxSpeed: Double = 1.4/60
  // Based on personal observations humans accelerate from 0 to full speed in around one second.
  val MaxForce: Double = MaxSpeed/60
  // Human takes about a quarter of square meter of space so his radius is around half that.
  val Radius: Double = 0.25
}