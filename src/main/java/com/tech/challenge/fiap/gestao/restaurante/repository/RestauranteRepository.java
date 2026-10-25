package com.tech.challenge.fiap.gestao.restaurante.repository;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    @Query("""
            select distinct r
            from Restaurante r
            left join fetch r.enderecos e
            left join fetch r.donoRestaurante u
            where (:#{#request.nome} is null or lower(r.nome) like lower(concat('%', :#{#request.nome}, '%')))
              and (:#{#request.tipoCozinha} is null or r.tipoCozinha = :#{#request.tipoCozinha})
              and (:#{#request.estado} is null or lower(e.estado) like lower(concat('%', :#{#request.estado}, '%')))
              and (:#{#request.cidade} is null or lower(e.cidade) like lower(concat('%', :#{#request.cidade}, '%')))
              and (:#{#request.donoRestaurante} is null or lower(u.nome) like lower(concat('%', :#{#request.donoRestaurante}, '%')))
            """)
    Set<Restaurante> findAllCustom(@Param("request") ConsultarRestauranteDTO request);

    Restaurante findByNomeAndDonoRestaurante_Nome(String nome, String donoRestaurante);
}
