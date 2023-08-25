package com.kezlo.security.exception;

import java.util.Objects

enum class ErrorCode(
  var code: String,
  var message: String
) {
  SUCCESS("0000", "Success"),
  FAIL("0001", "Fail")
  ;

  fun valueAsString(objects: Array<Objects> ): String {
    var sb = StringBuilder().append(code).append(" - ").append(message);
    return String.format(sb.toString(), objects);
  }

  override fun toString(): String {
    return "ErrorCode(code='$code', message='$message')"
  }


}
