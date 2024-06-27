package cn.koha.modelica.ffi;

import com.oracle.truffle.api.interop.TruffleObject;

import java.nio.file.Path;
import java.util.List;

public interface NativeAccess {
    TruffleObject loadLibrary(Path libPath);
    default TruffleObject loadLibrary(List<Path> searchPaths, String shortName, boolean notFoundIsFatal) {
        for (Path path : searchPaths) {
            Path libPath = path.resolve(System.mapLibraryName(shortName));
            TruffleObject library = loadLibrary(libPath.toAbsolutePath());
            if (library != null) {
                return library;
            }
        }
        if (notFoundIsFatal) {
            throw new RuntimeException("library not found");
//            throw EspressoError.shouldNotReachHere("Cannot load library: " + shortName + "\nSearch path: " + searchPaths);
        }
        return null;
    }
    void unloadLibrary(TruffleObject lib);
    TruffleObject lookupSymbol(TruffleObject library, String symbolName);
    TruffleObject bindSymbol(TruffleObject symbol, NativeSignature signature);
    default TruffleObject lookupAndBindSymbol(TruffleObject library, String symbolName, NativeSignature nativeSignature) {
        TruffleObject symbol = lookupSymbol(library, symbolName);
        if (symbol == null) {
            // not found
            return null;
        }
        return bindSymbol(symbol, nativeSignature);
    }
}
