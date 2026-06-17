package org.example.tarea.services;

import org.example.tarea.mappers.CategoriaMapper;
import org.example.tarea.models.dtos.CategoriaDTO;
import org.example.tarea.models.entities.Categoria;
import org.example.tarea.repos.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;


    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public CategoriaDTO crearCategoria(CategoriaDTO unaCategoria) {
        Categoria categoria = categoriaMapper.categoriaDtoToCategoria(unaCategoria);
        categoria.setId(null);
        return categoriaMapper.categoriaToDto(categoriaRepository.save(categoria));
    }

    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::categoriaToDto)
                .toList();
    }

    public ResponseEntity<CategoriaDTO> actualizarCategoria(Long id, CategoriaDTO unaCategoria) {
        Optional<Categoria> existente = categoriaRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Categoria categoria = existente.get();
        categoria.setNombre(unaCategoria.getNombre());
        categoria.setDescripcion(unaCategoria.getDescripcion());
        return ResponseEntity.ok(categoriaMapper.categoriaToDto(categoriaRepository.save(categoria)));
    }

    public ResponseEntity<CategoriaDTO> buscarCategoria(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaMapper.categoriaToDto(categoria.get()));
    }

    public ResponseEntity<String> borrarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.ok("Se borró la categoria con id " + id);
    }
}
