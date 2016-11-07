package bio4j.data.uniref.test

import org.scalatest.FunSuite
import bio4j.test.ReleaseOnlyTest
import com.bio4j.data.uniref._

class ParseUniRef50 extends FunSuite {

  def lines: Iterator[String] =
    io.Source.fromFile("frg.xml").getLines

  def bigLines: Iterator[String] =
    io.Source.fromFile("uniref50.xml").getLines

  test("parse small fragment, access all data") {

    xml.entries.fromLines(lines) foreach { e =>

      val ID = e.ID
      val seedID = e.seedID
      val representativeID = e.representativeID
      val nonRepresentativeMemberIDs = e.nonRepresentativeMemberIDs
    }
  }

  // this parses ~15*10^4 entries
  test("parse big fragment, access all data", ReleaseOnlyTest) {

    xml.entries.fromLines(bigLines) foreach { e =>

      val ID = e.ID
      val seedID = e.seedID
      val representativeID = e.representativeID
      val nonRepresentativeMemberIDs = e.nonRepresentativeMemberIDs
    }
  }

}
