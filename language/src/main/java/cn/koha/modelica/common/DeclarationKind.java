package cn.koha.modelica.common;

public enum DeclarationKind {
    PARAMETER,
    CONSTANT;
    public static DeclarationKind fromToken(String token) {
        return switch (token) {
            case "parameter" -> PARAMETER;
            default -> CONSTANT;
        };
    }
}
