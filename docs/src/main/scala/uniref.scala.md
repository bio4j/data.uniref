
```scala
package com.bio4j.data.uniref

trait AnyCluster extends Any {

  def ID: String
  def seedID: Option[String]
  def representativeID: Option[String]
  def nonRepresentativeMemberIDs: Seq[String]
}

```




[test/scala/ParseUniRef50.scala]: ../../test/scala/ParseUniRef50.scala.md
[main/scala/uniref.scala]: uniref.scala.md
[main/scala/xml/parsers.scala]: xml/parsers.scala.md
[main/scala/xml/entry.scala]: xml/entry.scala.md