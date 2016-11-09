
```scala
package com.bio4j.data.uniref.xml

import com.bio4j.data.uniref._
import scala.xml._
import scala.compat.java8.OptionConverters._
```


This class assumes that we are working with an UniProt cluster


```scala
case class Entry(val xml: Elem) extends AnyVal with AnyCluster {

  def ID: String =
    xml \ "@id" text

  def representative: ClusterMember =
    dbRefToClusterMember(representativedbRef)

  def seed: ClusterMember =
    if(dbRefIsSeed(representativedbRef))
      representative
    else
      dbRefToClusterMember(
        nonRepresentativeMembersdbRefs
          .filter(dbRefIsSeed)
          .head
      )

  def nonRepresentativeMembers: Seq[ClusterMember] =
    nonRepresentativeMembersdbRefs map dbRefToClusterMember

  private def dbRefToClusterMember(dbRef: Node): ClusterMember = {

    val id = dbRef \ "@id" text

    if(isUniProtDBReference(dbRef))
      UniProtProtein(id)
    else
      UniParcProtein(id)
  }

  private def isUniProtDBReference(dbRef: Node): Boolean =
    (dbRef \ "@type" text) == "UniProtKB ID"

  private def isUniParcDBReference(dbRef: Node): Boolean =
    (dbRef \ "@type" text) == "UniParc ID"

  private def dbRefIsSeed(dbRef: Node): Boolean =
    (dbRef \ "property") exists isSeed

  private def isSeed(property: Node): Boolean =
    (property \ "@type" text) == "isSeed"

  private def representativedbRef: Node =
    xml \ "representativeMember" \ "dbReference" head

  private def nonRepresentativeMembersdbRefs =
    (xml \ "member" \ "dbReference")
}

```




[test/scala/ParseUniRef50.scala]: ../../../test/scala/ParseUniRef50.scala.md
[main/scala/uniref.scala]: ../uniref.scala.md
[main/scala/xml/parsers.scala]: parsers.scala.md
[main/scala/xml/entry.scala]: entry.scala.md