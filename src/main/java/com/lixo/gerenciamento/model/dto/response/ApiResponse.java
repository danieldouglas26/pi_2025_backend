package com.lixo.gerenciamento.model.dto.response;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private Object errors;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, T data, String message, Object errors) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errors = errors;
    }

    public ApiResponse(boolean success) {
        this.success = success;
    }

    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, Object errors) {
        this.success = success;
        this.message = message;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ApiResponse<?> that = (ApiResponse<?>) o;
        
        if (success != that.success) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return errors != null ? errors.equals(that.errors) : that.errors == null;
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (errors != null ? errors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, null, message, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }

    public static <T> ApiResponse<T> error(String message, Object errors) {
        return new ApiResponse<>(false, message, errors);
    }
    
    public static <T> ApiResponse<T> of(boolean success) {
        return new ApiResponse<>(success);
    }
    
    public static <T> ApiResponse<T> of(boolean success, T data) {
        return new ApiResponse<>(success, data);
    }
    
    public static <T> ApiResponse<T> of(boolean success, T data, String message) {
        return new ApiResponse<>(success, data, message);
    }
    
    public static <T> ApiResponse<T> of(boolean success, T data, String message, Object errors) {
        return new ApiResponse<>(success, data, message, errors);
    }
}