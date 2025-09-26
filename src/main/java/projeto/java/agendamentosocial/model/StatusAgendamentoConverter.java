package projeto.java.agendamentosocial.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusAgendamentoConverter implements AttributeConverter<StatusAgendamento, String> {

    @Override
    public String convertToDatabaseColumn(StatusAgendamento status) {
        if (status == null) return null;
        return status.name();
    }

    @Override
    public StatusAgendamento convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        switch (dbData.trim().toLowerCase()) {
            case "pendente": return StatusAgendamento.PENDENTE;
            case "confirmado": return StatusAgendamento.CONFIRMADO;
            case "cancelado": return StatusAgendamento.CANCELADO;
            default:
                throw new IllegalArgumentException("Status inválido: " + dbData);
        }
    }
}

