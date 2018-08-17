package br.com.conductor.devchallenge.controller;

import br.com.conductor.devchallenge.entity.InterfaceEntity;
import br.com.conductor.devchallenge.service.AbstractService;

/**
 * Interface responsável por reunir comportamentos em comum de todos os controladores
 * @param <T> Tipo da Entidade principal processado pelo controlador que a implementar
 */
public interface InterfaceController<T extends InterfaceEntity<?>> {
    AbstractService<T, ?> getService();
}