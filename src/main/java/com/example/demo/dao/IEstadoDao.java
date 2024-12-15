package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Estado;

public interface IEstadoDao extends JpaRepository<Estado, Integer>{

}
