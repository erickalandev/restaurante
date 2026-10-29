package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.repository;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultaUsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("""
            select distinct u
            from UsuarioEntity u
            left join fetch u.enderecoEntities e
            where (:#{#request.nome} is null or lower(u.nome) like lower(concat('%', :#{#request.nome}, '%')))
              and (:#{#request.cpf} is null or u.cpf = :#{#request.cpf})
              and (:#{#request.email} is null or lower(u.email) like lower(concat('%', :#{#request.email}, '%')))
              and (:#{#request.contato} is null or u.contato like concat('%', :#{#request.contato}, '%'))
            """)
    Set<UsuarioEntity> findAllCustom(@Param("request") ConsultaUsuarioDTO request);

    UsuarioEntity findByCpf(String cpf);
}
