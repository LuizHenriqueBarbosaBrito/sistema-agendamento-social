package projeto.java.agendamentosocial.model;

import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class SexoConverter implements AttributeConverter<Sexo, String> {

    @Override
    public String convertToDatabaseColumn(Sexo sexo) {
        if (sexo == null) return null;
        return switch (sexo) {
            case Masculino -> "Masculino"; // Banco salva assim
            case Feminino -> "Feminino";
            case Outro -> "outro";

        };
    }

    @Override
    public Sexo convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData.toLowerCase()) {
            case "masculino" -> Sexo.Masculino;
            case "feminino"  -> Sexo.Feminino;
            case "outro"  -> Sexo.Outro;
            default -> throw new IllegalArgumentException("Valor inválido para Sexo: " + dbData);
        };
    }

}



