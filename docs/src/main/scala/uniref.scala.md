
```scala
package com.bio4j.data.uniref

trait AnyCluster extends Any {

  def ID: String
  def seed: ClusterMember
  def representative: ClusterMember
  def nonRepresentativeMembers: Seq[ClusterMember]
}

sealed trait ClusterMember extends Any {
  def id: String
}
case class UniProtProtein(val id: String) extends ClusterMember
case class UniParcProtein(val id: String) extends ClusterMember

```




[test/scala/ParseUniRef50.scala]: ../../test/scala/ParseUniRef50.scala.md
[main/scala/uniref.scala]: uniref.scala.md
[main/scala/xml/parsers.scala]: xml/parsers.scala.md
[main/scala/xml/entry.scala]: xml/entry.scala.md