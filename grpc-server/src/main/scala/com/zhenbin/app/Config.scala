package com.zhenbin.app

import pureconfig.ConfigSource
import com.zhenbin.app.db.DBConfig
import pureconfig.ConfigSource.resources
import pureconfig.error.ConfigReaderFailures

case class Config(
    db: DBConfig
)

class ConfigException(failures: ConfigReaderFailures)
    extends Exception(failures.prettyPrint())

object Config {
  def load(name: Option[String]): Config = {
    import pureconfig.generic.auto._
    name match {
      case Some(config) =>
        ConfigSource.default(resources(config)).load[Config] match {
          case Right(c) => c
          case Left(es) => throw new ConfigException(es)
        }
      case None =>
        ConfigSource.default.load[Config] match {
          case Right(c) => c
          case Left(es) => throw new ConfigException(es)
        }
    }
  }
}
