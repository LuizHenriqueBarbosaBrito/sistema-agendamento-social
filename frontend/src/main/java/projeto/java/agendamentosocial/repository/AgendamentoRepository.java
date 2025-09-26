package projeto.java.agendamentosocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.java.agendamentosocial.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
