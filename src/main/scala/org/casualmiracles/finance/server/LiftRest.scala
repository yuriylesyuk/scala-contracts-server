package org.casualmiracles.finance.server

import com.typesafe.scalalogging._

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.LiftRules
import net.liftweb.http.PlainTextResponse
import net.liftweb.http.JsonResponse 
import net.liftweb.http.OkResponse
import net.liftweb.http.PostRequest
import net.liftweb.common.Full

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

import net.liftweb.json.Extraction._
import net.liftweb.json.Printer._
import net.liftweb.json.DefaultFormats

object LiftRest extends RestHelper with LazyLogging{

    serve ( "api" / "v1"  prefix {
      case "list" :: Nil JsonGet req => 
        ("xx" -> "A xx") ~
        ("yy" -> "A yy")
        
      case Options( _,  req ) => OkResponse()
     
      case "echoexcontr" :: Nil JsonPost ( (jsongram, req) ) => JsonResponse( echoexcontr(jsongram) )
      
      case "lattice" :: Nil JsonPost ( (jsongram, req) ) => JsonResponse( lattice(jsongram) )

      case "dot" :: Nil Post req => PlainTextResponse( dot( req.json.get ) )

      case "expvalchart" :: Nil Post req => PlainTextResponse( expvalchart( req.json.get ) )
    })
    
    def init(): Unit = {
          LiftRules.statelessDispatch.append(LiftRest)
    }

    
  def echoexcontr( jsonval:JValue ): JValue = {    
     implicit val formats = DefaultFormats
     
     logger.info( "Received Json: " + jsonval)
     
     val excontr = jsonval.extract[ExContr]
     
     decompose(excontr)
   }
  
  def lattice(jsonval: JValue): JValue = {
    
    decompose( ContractsAPI.lattice(jsonval.extract[ExContr]) )
  }
  
  def dot( jsonval: JValue ): String = {
    
    ContractsAPI.computeContact(jsonval.extract[ExContr])
  }
  
  def expvalchart( jsonval: JValue ): String = {
    // TODO: add check for probs contractId - return nothing in the case:
    // -- expected value is meaningless for the probabilities it relies on
    ContractsAPI.expValChart(jsonval.extract[ExContr])
  }
}

  
  