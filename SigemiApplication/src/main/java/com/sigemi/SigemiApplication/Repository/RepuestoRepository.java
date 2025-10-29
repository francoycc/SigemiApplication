/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.Repuesto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
    // buscar repuestos que estan por debajo del stock minimo
    List<Repuesto> findByStockActualLessThan(Integer stockMinimo);
    
}
