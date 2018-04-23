package ryysissimulaatio

/**
 * Kuvaa kaksiulotteista matemaattista vektoria
 */
case class Vector2D(x: Double, y: Double) {

  val length = math.hypot(x, y)
  val angle  = math.atan2(y, x)

  def * (scalar: Double) = Vector2D(x*scalar, y*scalar)
  
  def / (scalar: Double) = Vector2D(x/scalar, y/scalar)
  
  def unary_- = Vector2D(-x, -y)
  
  // Returns a unit vector of given vector
  def normalize() = Vector2D(x/length, y/length)
  
  // Truncates vector to given value
  def truncate(maxLength: Double) = {
    if(this.length > maxLength) Vector2D(x/length*maxLength, y/length*maxLength)
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
