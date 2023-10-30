package exceptions

class ProcessReturnCodeException(process: String, code: Int, stdErr: String) :
    Exception("Process $process executed with non-zero return code='$code'! Error stream:\n$stdErr")