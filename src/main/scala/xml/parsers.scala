package com.bio4j.data.uniref.xml

import scala.xml._
import scala.compat.java8.OptionConverters._

case object entries {

  implicit def asXML: Entry => Elem = _.xml

  /*
    This method is an ugly hack for getting an iterator of entries from UniProt xml.
  */
  def fromLines(lines: Iterator[String]): Iterator[Entry] = new Iterator[Entry] {

    private val rest: BufferedIterator[String] = lines.buffered
    private val currentEntryStringBuilder = new collection.mutable.StringBuilder

    private var _hasNextCalled: Boolean = false
    private var _hasNext      : Boolean = false

    private lazy val MyXML = new factory.XMLLoader[Elem] {

      private lazy val parserFactory = {
        val f0 = javax.xml.parsers.SAXParserFactory.newInstance()
        f0.setNamespaceAware(false)
        f0.setValidating(false)
        f0.setXIncludeAware(false)
        f0
      }

      private lazy val parser0 = parserFactory.newSAXParser()

      override def parser: SAXParser = parser0
    }

    /*
      note that internally hasNext drops everything it founds before a line starting with '<entry'.
    */
    def hasNext: Boolean = if(_hasNextCalled) _hasNext else {

      _hasNext = advanceUntilNextEntry
      _hasNextCalled = true
      _hasNext
    }

    def next(): Entry = {

      if(hasNext) {
        _hasNextCalled = false;
        takeEntry
      }
      else throw new NoSuchElementException
    }

    private def isEntryStart(line: String): Boolean = line startsWith "<entry"
    private def isEntryStop(line: String): Boolean  = line startsWith "</entry"

    private def advanceUntilNextEntry: Boolean = {

      if(rest.hasNext) {

        val nextLine = rest.head

        if(isEntryStart(nextLine))
          true
        else {
          rest.next
          advanceUntilNextEntry
        }
      }
      else
        false
    }

    @annotation.tailrec
    private def takeEntry_rec(acc: collection.mutable.StringBuilder): Entry =
      if( !isEntryStop(rest.head) )
        takeEntry_rec(acc ++= rest.next)
      else
        Entry( MyXML.loadString( (acc ++= rest.next).toString ) )

    private def takeEntry: Entry = {
      currentEntryStringBuilder.setLength(0) // clear the string builder
      takeEntry_rec(currentEntryStringBuilder)
    }
  }
}
