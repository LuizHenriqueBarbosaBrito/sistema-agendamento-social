package projeto.java.agendamentosocial.dto;

import projeto.java.agendamentosocial.model.Beneficiario;
import projeto.java.agendamentosocial.model.StatusAgendamento;
import projeto.java.agendamentosocial.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoResponseDT0 {

    private Long id;
    private Usuario usuario;
    private Beneficiario beneficiario;
    private LocalDate dataAgendamento;
    private LocalTime horaAgendamento;
    private String tipoServico;
    private StatusAgendamento status;
    private String localAtendimento;
    private String observacoes;


    public AgendamentoResponseDT0(Long id, Usuario usuario, Beneficiario beneficiario,
                                  LocalDate dataAgendamento, LocalTime horaAgendamento,
                                  String tipoServico, StatusAgendamento status,
                                  String localAtendimento, String observacoes) {
        this.id = id;
        this.usuario = usuario;
        this.beneficiario = beneficiario;
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
        this.tipoServico = tipoServico;
        this.status = status;
        this.localAtendimento = localAtendimento;
        this.observacoes = observacoes;
    }


    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public LocalTime getHoraAgendamento() {
        return horaAgendamento;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public String getLocalAtendimento() {
        return localAtendimento;
    }

    public String getObservacoes() {
        return observacoes;
    }
}

