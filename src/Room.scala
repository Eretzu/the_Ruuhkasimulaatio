import scala.collection.mutable.ArrayBuffer
class Room(val width: Int, val height: Int, humanAmount: Int) {
  var humans = ArrayBuffer.fill(humanAmount)((0, 0))
  
  def runRound() = humans.foreach(human => SteeringAlgorithm.getAcceleration(human._1, human._2, this))
}