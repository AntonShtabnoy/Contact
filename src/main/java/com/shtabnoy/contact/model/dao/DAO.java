package com.shtabnoy.contact.model.dao;

import java.sql.SQLException;
import java.util.List;
/**
 * Унифицированный интерфейс управления персистентным состоянием объектов
 * @param <T> тип объекта персистенции
 * @param <PK> тип первичного ключа
 */
public interface DAO <T,PK> {

    int insert(T contact) throws SQLException;
    int update(T contact) throws SQLException;
    void delete(PK contactId) throws SQLException;
    T getContact(PK contactId) throws SQLException;
    List<T> findContacts(int displacement, int count) throws SQLException;
    int getCountContacts() throws SQLException;




}
