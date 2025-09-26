package projeto.java.agendamentosocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.java.agendamentosocial.model.Beneficiario;
import projeto.java.agendamentosocial.repository.BeneficiarioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beneficiarios")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @GetMapping
    public List<Beneficiario> listarTodos() {
        return beneficiarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficiario> buscarPorId(@PathVariable Long id) {
        Optional<Beneficiario> beneficiario = beneficiarioRepository.findById(id);
        return beneficiario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Beneficiario criar(@RequestBody Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficiario> atualizar(@PathVariable Long id, @RequestBody Beneficiario beneficiarioAtualizado) {
        Optional<Beneficiario> beneficiarioExistente = beneficiarioRepository.findById(id);

        if (beneficiarioExistente.isPresent()) {
            Beneficiario beneficiario = beneficiarioExistente.get();

            beneficiario.setNome(beneficiarioAtualizado.getNome());
            beneficiario.setCpf(beneficiarioAtualizado.getCpf());
            beneficiario.setSexo(beneficiarioAtualizado.getSexo());
            beneficiario.setDataNascimento(beneficiarioAtualizado.getDataNascimento());
            beneficiario.setTelefone(beneficiarioAtualizado.getTelefone());
            beneficiario.setEmail(beneficiarioAtualizado.getEmail());
            beneficiario.setEndereco(beneficiarioAtualizado.getEndereco());
            beneficiario.setBairro(beneficiarioAtualizado.getBairro());

            beneficiarioRepository.save(beneficiario);
            return ResponseEntity.ok(beneficiario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (beneficiarioRepository.existsById(id)) {
            beneficiarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

