package com.herkat.exceptions;

public enum ErrorMessage {

    BANNER_NOT_FOUND("Banner no encontrado."),
    CLIENT_NOT_FOUND("Cliente no encontrado."),
    IMAGE_NOT_FOUND("Imagen no encontrada."),
    PRODUCT_NOT_FOUND("Producto no encontrado."),
    PRODUCT_TYPE_NOT_FOUND("Tipo de producto no encontrado."),
    SERVICE_ITEM_NOT_FOUND("Servicio no encontrado."),
    SERVICE_ITEM_TYPE_NOT_FOUND("Tipo de servicio no encontrado."),
    MACHINE_NOT_FOUND("Máquina no encontrada."),
    MACHINE_TYPE_NOT_FOUND("Tipo de máquina no encontrada."),
    SOCIAL_MEDIA_NOT_FOUND("Red social no encontrada."),
    ITEM_NOT_FOUND("Ítem no encontrado."),
    INVALID_QUANTITY("Cantidad no válida."),
    INSUFFICIENT_STOCK("Stock insuficiente."),
    BALANCE_NOT_FOUND("Balance no encontrado."),
    MOVEMENT_NOT_FOUND("Movimiento no encontrado."),
    INVALID_REQUEST("Solicitud no válida."),
    DATABASE_ERROR("Ocurrió un error en la base de datos."),
    INTERNAL_SERVER_ERROR("Error interno del servidor."),
    DUPLICATE_RECORD("Registro ya existente.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
