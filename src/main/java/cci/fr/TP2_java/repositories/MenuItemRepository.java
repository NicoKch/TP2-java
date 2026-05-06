package cci.fr.TP2_java.repositories;

import cci.fr.TP2_java.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}