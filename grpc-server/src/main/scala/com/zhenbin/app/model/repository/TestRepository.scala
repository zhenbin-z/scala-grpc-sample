package com.zhenbin.app.model.repository

trait TestRepository[M[_]] {
  def getTestContent: M[Option[String]]
}
