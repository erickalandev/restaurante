package com.tech.challenge.fiap.gestao.restaurante.repository;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultaUsuarioRequest;
import com.tech.challenge.fiap.gestao.restaurante.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
            select distinct u
            from Usuario u
            left join fetch u.enderecos e
            where (:#{#request.nome} is null or lower(u.nome) like lower(concat('%', :#{#request.nome}, '%')))
              and (:#{#request.cpf} is null or u.cpf = :#{#request.cpf})
              and (:#{#request.email} is null or lower(u.email) like lower(concat('%', :#{#request.email}, '%')))
              and (:#{#request.contato} is null or u.contato like concat('%', :#{#request.contato}, '%'))
            """)
    Set<Usuario> findAllCustom(@Param("request") ConsultaUsuarioRequest request);

    Usuario findByCpf(String cpf);
}
