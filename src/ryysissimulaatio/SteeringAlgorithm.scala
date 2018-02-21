import ryysissimulaatio.Room


package ryysissimulaatio

object SteeringAlgorithm {
  def getAcceleration ( x: Float, y: Float, room:Room ) : (Float, Float) = {
    var returnVector = ( 0f, 0f )
    returnVector = addVectors( returnVector, seek )
    returnVector = addVectors( returnVector, brake )
    returnVector = addVectors( returnVector, avoid )
    return returnVector
  }
  
  private def seek () : (Float, Float) = ???
  
  private def brake () : (Float, Float) = ???
  
  private def avoid () : (Float, Float) = ???
  
  private def addVectors ( one:(Float, Float), two:(Float, Float) ) : (Float, Float) = ( one._1 + two._1, one._2 + two._2)
}
