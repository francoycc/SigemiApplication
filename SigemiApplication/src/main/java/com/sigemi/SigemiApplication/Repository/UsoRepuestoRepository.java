/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.UsoRepuesto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsoRepuestoRepository extends JpaRepository<UsoRepuesto, Long> {
    List<UsoRepuesto> findByOrden_IdOrden(Long idOrden);
    List<UsoRepuesto> findByRepuesto_IdRepuesto(Long idRepuesto);
}
