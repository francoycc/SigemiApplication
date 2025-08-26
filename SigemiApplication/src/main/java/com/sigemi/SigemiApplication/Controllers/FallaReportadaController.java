
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.FallaReportadaDTO;
import com.sigemi.SigemiApplication.Service.FallaService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/fallas")
@RequiredArgsConstructor
public class FallaReportadaController {

    @Autowired
    private final FallaService fallaService;

    public FallaReportadaController(FallaService fallaService) {
        this.fallaService = fallaService;
    }

    @PostMapping
    public ResponseEntity<FallaReportadaDTO> reportarFalla(@RequestBody FallaReportadaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fallaService.reportarFalla(dto));
    }

    @GetMapping
    public ResponseEntity<List<FallaReportadaDTO>> listarFallas() {
        return ResponseEntity.ok(fallaService.listarFallas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FallaReportadaDTO> obtenerFalla(@PathVariable Long id) {
        return ResponseEntity.ok(fallaService.obtenerFallaPorId(id));
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
