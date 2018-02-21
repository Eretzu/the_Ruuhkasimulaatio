package ryysissimulaatio

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Room(val width: Int, val height: Int, humanAmount: Int) {
  val rand = new Random
  var humans = ArrayBuffer.fill[(Float, Float)](humanAmount)((rand.nextInt(width), rand.nextInt(height)))
  
  def runRound() = humans.foreach(human => SteeringAlgorithm.getAcceleration(human._1, human._2, this))
}