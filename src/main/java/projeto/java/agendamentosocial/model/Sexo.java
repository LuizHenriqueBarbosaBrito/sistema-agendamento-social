package projeto.java.agendamentosocial.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sexo {
    Masculino,
    Feminino,
    Outro;

    @JsonCreator
    public static Sexo fromString(String value) {
        if (value == null) return null;
        return switch (value.toLowerCase()) {
            case "masculino" -> Sexo.Masculino;
            case "feminino"  -> Sexo.Feminino;
            case "outro"     -> Sexo.Outro;
            default -> throw new IllegalArgumentException("Valor inválido para Sexo: " + value);
        };
    }
}


