package br.edu.fatecpg.tecprog.springweb.tarefa.controller;

import br.edu.fatecpg.tecprog.springweb.tarefa.model.Tarefa;
import br.edu.fatecpg.tecprog.springweb.tarefa.repository.TarefaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaRepository repository;

    public TarefaController(TarefaRepository repository) {
        this.repository = repository;
    }

    private boolean invalida(Tarefa t) {
        boolean descricaoVazia = t.getDescricao() == null || t.getDescricao().isBlank();
        boolean prioridadeFora = t.getPrioridade() != null
                && (t.getPrioridade() < 1 || t.getPrioridade() > 5);
        return descricaoVazia || prioridadeFora;
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefa) {
        if (invalida(tarefa)) {
            return ResponseEntity.badRequest().build();
        }
        tarefa.setId(null);
        if (tarefa.getConcluida() == null) {
            tarefa.setConcluida(false);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tarefa));
    }

    @GetMapping
    public List<Tarefa> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa dados) {
        if (invalida(dados)) {
            return ResponseEntity.badRequest().build();
        }
        return repository.findById(id)
                .map(tarefa -> {
                    tarefa.setDescricao(dados.getDescricao());
                    tarefa.setConcluida(dados.getConcluida() != null ? dados.getConcluida() : false);
                    tarefa.setPrioridade(dados.getPrioridade());
                    return ResponseEntity.ok(repository.save(tarefa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}