

object SteeringAlgorithm {
  def getAcceleration ( caller:Human, room:Room ) : Vector = {
    returnVector += seek
    returnVector += braking
    returnVector += avoidWall
    returnVector += avoidCollision
    return returnVector
  }
  
  def seek () : Vector = ???
  
  def braking () : Vector = ???
  
  def avoid () : Vector = ???
}
