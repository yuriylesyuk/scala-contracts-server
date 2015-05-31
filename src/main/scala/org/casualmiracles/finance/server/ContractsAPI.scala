package org.casualmiracles.finance.server

import org.casualmiracles.finance.contracts._
import org.casualmiracles.finance.models._
import org.casualmiracles.finance.contracts.Contracts._
import ExampleModel._
import org.casualmiracles.finance.contracts.Instruments._
import org.casualmiracles.finance.models._
import scala.Stream

case class ExContr( contract: String, arg1: Double, arg2: Double, image: Boolean )

object ContractsAPI {
    def sanitize(d:Double):Double = Math.min( Math.floor(d), 12.0)

    def computeContact(exContr: ExContr) ={
      val tolerance = 0.001
      
      val testK = andPr( liftPr((d:Double)=>d==100, takePr(10, bigK[Double](100))))
     
      //val testProb = probabilityLattice( (int)exContr.arg2 ).sum - 1 < tolerance
      
      val xm = ExampleModel.makeModel(mkDate(0))
      val evalX=evalC(xm,USD)
    
      val c1:Contract = zeroCouponBond(mkDate(exContr.arg1.toInt),exContr.arg2,USD)
      val pr1 = evalX(c1)
      
      val li = new LatticeImage()
          
      val dotImageString = li.dotGraph ( li.prToDot( pr1 )).map(_ + "\n").mkString("")
      
      dotImageString
    }
  
}