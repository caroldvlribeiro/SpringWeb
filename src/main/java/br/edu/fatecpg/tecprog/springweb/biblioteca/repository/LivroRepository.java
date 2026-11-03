package br.edu.fatecpg.tecprog.springweb.biblioteca.repository;

import br.edu.fatecpg.tecprog.springweb.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}