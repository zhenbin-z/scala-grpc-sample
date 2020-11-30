package com.zhenbin.app

import cats.effect.Bracket

package object db {
  type BracketThrowable[F[_]] = Bracket[F, Throwable]
}
