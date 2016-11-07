package com.bio4j.data.uniref

trait AnyCluster extends Any {

  def ID: String
  def seedID: String
  def representativeID: String
  def nonRepresentativeMemberIDs: Seq[String]
}
