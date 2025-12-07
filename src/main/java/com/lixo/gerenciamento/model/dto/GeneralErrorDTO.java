package com.lixo.gerenciamento.model.dto;

public class GeneralErrorDTO {
    private String field;
    private String message;

    public GeneralErrorDTO() {
    }

    public GeneralErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public GeneralErrorDTO(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        GeneralErrorDTO that = (GeneralErrorDTO) o;
        
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GeneralErrorDTO{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static GeneralErrorDTO of(String field, String message) {
        return new GeneralErrorDTO(field, message);
    }

    public static GeneralErrorDTO of(String message) {
        return new GeneralErrorDTO(message);
    }

    public static GeneralErrorDTO globalError(String message) {
        return new GeneralErrorDTO(null, message);
    }

    public static GeneralErrorDTO fieldError(String field, String message) {
        return new GeneralErrorDTO(field, message);
    }

    public boolean isGlobalError() {
        return field == null || field.trim().isEmpty();
    }

    public boolean isFieldError() {
        return !isGlobalError();
    }
}