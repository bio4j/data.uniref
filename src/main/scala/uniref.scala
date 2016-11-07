package com.bio4j.data.uniref

trait AnyCluster extends Any {

  def ID: String
  def seedID: Option[String]
  def representativeID: Option[String]
  def nonRepresentativeMemberIDs: Seq[String]
}
