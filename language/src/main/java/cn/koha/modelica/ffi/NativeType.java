package cn.koha.modelica.ffi;

public enum NativeType {
    VOID,
    BOOLEAN, // 1 byte
    BYTE,
    CHAR, // unsigned short
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,

    OBJECT, // word-sized handle
    POINTER,

}
