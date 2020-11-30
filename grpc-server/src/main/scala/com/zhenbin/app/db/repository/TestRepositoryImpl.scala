package com.zhenbin.app.db.repository

import cats.effect.IO
import doobie._
import doobie.implicits._
import com.zhenbin.app.model.repository.TestRepository

class TestRepositoryImpl(xa: Transactor[IO]) extends TestRepository {
  override def getTestContent: IO[Option[String]] =
    sql"""select content from test where id = 1"""
      .query[String].option.transact(xa)
}
