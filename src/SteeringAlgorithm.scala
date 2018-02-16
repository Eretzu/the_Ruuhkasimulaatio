

object SteeringAlgorithm {
  def getAcceleration ( x: Int, y: Int, room:Room ) : (Int, Int) = {
    var returnVector = ( 0, 0 )
    returnVector = addVectors( returnVector, seek )
    returnVector = addVectors( returnVector, brake )
    returnVector = addVectors( returnVector, avoid )
    return returnVector
  }
  
  private def seek () : (Int, Int) = ???
  
  private def brake () : (Int, Int) = ???
  
  private def avoid () : (Int, Int) = ???
  
  private def addVectors ( one:(Int, Int), two:(Int, Int) ) : (Int, Int) = ( one._1 + two._1, one._2 + two._2)
}
