package com.bio4j.data.uniref.xml

import com.bio4j.data.uniref._
import scala.xml._
import scala.compat.java8.OptionConverters._

/*
  This class assumes that we are working with an UniProt cluster
*/
case class Entry(val xml: Elem) extends AnyVal with AnyCluster {

  def ID: String =
    xml \ "@id" text

  def representativeID: String =
    (xml \ "representativeMember" \ "dbReference" \ "property" )
      .filter( isUniProtAccessionProperty )
      .map( _ \ "@value" text )
      .head

  def nonRepresentativeMemberIDs: Seq[String] =
    nonRepresentativeMembers
      .flatMap( dbRef =>
        (dbRef \ "property").filter(isUniProtAccessionProperty)
        .map(_ \ "@value" text)
      )

  def seedID: String =
    if( (xml \ "representativeMember" \ "dbReference" \ "property") exists isSeed  )
      representativeID
    else
      accession(
        nonRepresentativeMembers
          .filter( member => (member \ "property") exists isSeed )
          .head
      )

  private def isUniProtDBReference(dbRef: Node): Boolean =
    (dbRef \ "@type" text) == "UniProtKB ID"

  private def isUniProtAccessionProperty(property: Node): Boolean =
    (property \ "@type" text) == "UniProtKB accession"

  private def isSeed(property: Node): Boolean =
    (property \ "@type" text) == "isSeed"

  private def nonRepresentativeMembers =
    (xml \ "member" \ "dbReference") filter isUniProtDBReference

  private def accession(dbRef: Node): String =
    (dbRef \ "property").filter(isUniProtAccessionProperty).head text
}
