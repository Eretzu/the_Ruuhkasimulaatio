package ryysissimulaatio

// Vector2D from asteroid project but heavily modified
case class Vector2D(x: Double, y: Double) {

  val length = math.hypot(x, y)
  val angle  = math.atan2(y, x)

  def * (scalar: Double) = Vector2D(x*scalar, y*scalar)
  
  def / (scalar: Double) = {
    if(scalar == 0) Vector2D(x,y)
    else Vector2D(x/scalar, y/scalar)
  }
  
  def unary_- = Vector2D(-x, -y)
  
  // Returns a unit vector of given vector
  def normalize() = Vector2D(x, y)/length
  
  // Truncates vector length to given value
  def truncate(maxLength: Double) = {
    if(this.length > maxLength) Vector2D(x, y).normalize*maxLength
    else this
  }
  
  // Dot product of 2 vectors
  def dot (other: Vector2D): Double = this.x*other.x + this.y*other.y
  
  // Angle between 2 vectors
  def angle (other: Vector2D): Double = math.acos((this dot other)/(this.length*other.length))
  
  def + (other: Vector2D) = {
    Vector2D(x + other.x, y + other.y)    
  }  
  
  def - (other: Vector2D) = {
    Vector2D(x - other.x, y - other.y)    
  }
}
