package com.example.demo.services;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.dao.ICategoriaDao;
import com.example.demo.models.Categoria;
import com.example.demo.models.CategoriaDto;
import com.example.demo.utilidades.PaginationMod;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ICategoriaDao categoriaDao;

    @Override
    public PaginationMod<CategoriaDto> listarCategoriaDtoPaginado(String nombre, Pageable pageable) {
        Integer estadoExcluido = 3;
        Page<Categoria> paginacion = categoriaDao.findByNombreContainingAndEstadoIdNot(nombre, estadoExcluido, pageable);

        PaginationMod<CategoriaDto> paginationMod = new PaginationMod<>();
        CategoriaDto[] entityDtos = mapper.map(paginacion.getContent(), CategoriaDto[].class);
        paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
        return paginationMod;
    }

    @Override
    public Categoria guardar(Categoria categoria) {
        return categoriaDao.save(categoria);
    }

    @Override
    public Categoria buscarPorIdCategoria(Integer id) {
        return categoriaDao.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        categoriaDao.deleteById(id);
    }

    @Override
    public Categoria nombreExiste(String nombre) {
        return categoriaDao.findByNombre(nombre);
    }

    @Override
    public List<Categoria> listar() {
        return categoriaDao.findAll();
    }
}
