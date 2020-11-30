package com.zhenbin.app.model.repository

import cats.effect.IO

trait TestRepository {
  def getTestContent: IO[Option[String]]
}
