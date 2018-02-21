package ryysissimulaatio

object SteeringAlgorithm {
  def getAcceleration ( human: Human, room:Room ) : Vector2D = {
    var returnVector = new Vector2D(0,0)
    returnVector = returnVector + seek
    returnVector = returnVector + brake
    returnVector = returnVector + avoid
    return returnVector
  }
  
  private def seek () : Vector2D = ???
  
  private def brake () : Vector2D = ???
  
  private def avoid () : Vector2D = ???
}
