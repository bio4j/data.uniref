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
