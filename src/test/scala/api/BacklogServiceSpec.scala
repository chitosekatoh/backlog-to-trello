package api

import models.{BacklogIssueModel, BacklogProjectModel}
import org.scalatest.flatspec.AnyFlatSpec

class BacklogServiceSpec extends AnyFlatSpec{

  "BacklogService.parseToBacklogModel(ProjectModel)" should "keyがid, nameのStringを投げると、Listを返却する" in {
    val checkData: String = "{\"id\": \"12345\", \"name\": \"Name\"}"
     val result = new BacklogServiceImpl().parseToBacklogModel(checkData)

    assert(result===List(BacklogProjectModel(12345,"Name")))
  }

  "BacklogService.parseToBacklogModel(IssueModel)" should "keyがid, summaryのStringを投げると、Listを返却する" in {
    val checkData: String = "{\"id\": \"12345\", \"summary\": \"Summary\"}"
    val result = new BacklogServiceImpl().parseToBacklogModel(checkData)

    assert(result===List(BacklogIssueModel(12345,"Summary")))
  }

}
