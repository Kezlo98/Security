package com.kezlo.security.exception;

data class BusinessException(
  var errorCode: ErrorCode,
  var objects: String
): RuntimeException() {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BusinessException

    if (errorCode != other.errorCode) return false
    if (!objects.contentEquals(other.objects)) return false

    return true
  }

}
