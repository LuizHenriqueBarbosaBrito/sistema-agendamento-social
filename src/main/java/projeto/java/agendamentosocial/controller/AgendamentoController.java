package projeto.java.agendamentosocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projeto.java.agendamentosocial.dto.AgendamentoResponseDT0;
import projeto.java.agendamentosocial.model.Agendamento;
import projeto.java.agendamentosocial.repository.AgendamentoRepository;
import projeto.java.agendamentosocial.repository.BeneficiarioRepository;
import projeto.java.agendamentosocial.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        agendamentoRepository.deleteById(id);
    }


    @PostMapping
    public AgendamentoResponseDT0 criar(@RequestBody Agendamento agendamento) {

        var usuarioCompleto = usuarioRepository.findById(agendamento.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var beneficiarioCompleto = beneficiarioRepository.findById(agendamento.getBeneficiario().getId())
                .orElseThrow(() -> new RuntimeException("Beneficiário não encontrado"));

        agendamento.setUsuario(usuarioCompleto);
        agendamento.setBeneficiario(beneficiarioCompleto);

        Agendamento salvo = agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDT0(
                salvo.getId(),
                salvo.getUsuario(),
                salvo.getBeneficiario(),
                salvo.getDataAgendamento(),
                salvo.getHoraAgendamento(),
                salvo.getTipoServico(),
                salvo.getStatus(),
                salvo.getLocalAtendimento(),
                salvo.getObservacoes()
        );
    }




    @GetMapping
    public List<AgendamentoResponseDT0> listarTodos() {
        return agendamentoRepository.findAll().stream().map(a ->
                new AgendamentoResponseDT0(
                        a.getId(),
                        a.getUsuario(),
                        a.getBeneficiario(),
                        a.getDataAgendamento(),
                        a.getHoraAgendamento(),
                        a.getTipoServico(),
                        a.getStatus(),
                        a.getLocalAtendimento(),
                        a.getObservacoes()
                )
        ).toList();
    }



    @GetMapping("/{id}")
    public AgendamentoResponseDT0 buscarPorId(@PathVariable Long id) {
        return agendamentoRepository.findById(id)
                .map(salvo -> new AgendamentoResponseDT0(
                        salvo.getId(),
                        salvo.getUsuario(),
                        salvo.getBeneficiario(),
                        salvo.getDataAgendamento(),
                        salvo.getHoraAgendamento(),
                        salvo.getTipoServico(),
                        salvo.getStatus(),
                        salvo.getLocalAtendimento(),
                        salvo.getObservacoes()
                ))
                .orElse(null);
    }


    @PutMapping("/{id}")
    public AgendamentoResponseDT0 atualizar(@PathVariable Long id, @RequestBody Agendamento atualizado) {
        return agendamentoRepository.findById(id).map(agendamento -> {
            agendamento.setDataAgendamento(atualizado.getDataAgendamento());
            agendamento.setHoraAgendamento(atualizado.getHoraAgendamento());
            agendamento.setTipoServico(atualizado.getTipoServico());
            agendamento.setStatus(atualizado.getStatus());
            agendamento.setLocalAtendimento(atualizado.getLocalAtendimento());
            agendamento.setObservacoes(atualizado.getObservacoes());

            Agendamento salvo = agendamentoRepository.save(agendamento);

            return new AgendamentoResponseDT0(
                    salvo.getId(),
                    salvo.getUsuario(),
                    salvo.getBeneficiario(),
                    salvo.getDataAgendamento(),
                    salvo.getHoraAgendamento(),
                    salvo.getTipoServico(),
                    salvo.getStatus(),
                    salvo.getLocalAtendimento(),
                    salvo.getObservacoes()
            );
        }).orElse(null);
    }

}


