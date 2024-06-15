package com.challengeliteralura.literatura.service;

public interface IConvertData {
    <T> T obtenerDatos(String json, Class<T> clase);
}
