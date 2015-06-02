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

    private def makePr1(exContr: ExContr): PR[Double] = {
      val xm = ExampleModel.makeModel(mkDate(0))
      val evalX=evalC(xm,USD)
    
      val c1:Contract = zeroCouponBond(mkDate(exContr.arg1.toInt),exContr.arg2,USD)
      evalX(c1)
    }
    
    def computeContact(exContr: ExContr): String ={
      val tolerance = 0.001
       
      val pr1 = makePr1(exContr)      
      
      val li = new LatticeImage()
          
      val dotImageString = li.dotGraph ( li.prToDot( pr1 )).map(_ + "\n").mkString("")
      
      dotImageString
    }
  
    def expValChart(exContr: ExContr): String = {
      val pr1 = makePr1(exContr)
      
      GoogleCharts.chartUrl( expectedValuePr(pr1) )
    }
}