package ryysissimulaatio

/**
 * Kuvaa kaksiulotteista matemaattista vektoria
 */
case class Vector2D(x: Double, y: Double) {

  val length = math.hypot(x, y)
  val angle  = math.atan2(y, x)

  def * (scalar: Double) = Vector2D(x*scalar, y*scalar)
  
  def normalize() = this.truncate(1.0)
  
  def truncate(maxLength: Double) = {
    if(this.length > maxLength) Vector2D(x/length*maxLength, y/length*maxLength)
    else this
  }
  
  /**
   * Lakee yhteen kaksi vektoria ja palauttaa uuden vektorin
   */
  def + (other: Vector2D) = {
    Vector2D(x + other.x, y + other.y)    
  }  
  
  def - (other: Vector2D) = {
    Vector2D(x - other.x, y - other.y)    
  }

  /**
   * Koordinaatit "pyörähtävät ympäri" jotta asteroidit putkahtavat uudestaan toiselta puolelta
   * bound saa parametreina ikkunan koon ja kappaleen koon.
   */
  
  /*
  def bound(xBound: Int, shapeWidth: Int, yBound: Int, shapeHeight: Int) = {
    val newX = 
      if (x >= xBound+shapeWidth) 
        x - xBound - 2* shapeWidth
      else if (x < -shapeWidth)
        x + xBound + 2* shapeWidth
      else x
      
    val newY = 
      if (y >= yBound+shapeHeight) 
        y - yBound - 2* shapeHeight
      else if (y < -shapeHeight)
        y + yBound + 2*shapeHeight
      else y
        
    if (newX != x || newY != y)
      Vector2D(newX, newY)
    else
      this
  }*/
}
