package projetojavavscode.safeflame.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import projetojavavscode.safeflame.controller.UsuarioController;
import projetojavavscode.safeflame.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioSpecification {
    public static Specification<Usuario> withFilters(UsuarioController.UsuarioFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.nomeCompleto() != null && !filtro.nomeCompleto().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nomeCompleto")), "%" + filtro.nomeCompleto().toLowerCase() + "%"));
            }

            if (filtro.email() != null && !filtro.email().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("email")), "%" + filtro.email().toLowerCase() + "%"));
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);

            return cb.and(arrayPredicates);

        };
    }
}
