package br.com.conductor.devchallenge.entity;

import java.io.Serializable;

/**
 * Interface responsável por reunir comportamentos em comum de todos as entidades
 * @param <ID> Tipo do ID da entidade principal
 */
public interface InterfaceEntity<ID> extends Serializable {
    ID getId();

    void setId(ID id);
}
