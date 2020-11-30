package com.zhenbin.app.db.repository

import doobie._
import doobie.implicits._
import com.zhenbin.app.db.BracketThrowable
import com.zhenbin.app.model.repository.TestRepository

class TestRepositoryImpl[F[_]: BracketThrowable](xa: Transactor[F])
    extends TestRepository[F] {
  override def getTestContent: F[Option[String]] =
    sql"""select content from test where id = 1"""
      .query[String].option.transact(xa)
}
