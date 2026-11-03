package br.edu.fatecpg.tecprog.springweb.tarefa.repository;

import br.edu.fatecpg.tecprog.springweb.tarefa.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}