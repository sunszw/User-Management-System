package com.ssmsun.management.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * JSON数据封装
 * @param <T>
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Json<T>  implements Serializable {
    private static final long serialVersionUID = 4955800029291101054L;

    private Integer state;
    private String message;
    private T data;

    public Json(Integer state) {
        this.state = state;
    }

    public Json(String message) {
        this.message = message;
    }

    public Json(Integer state, T data) {
        this.state = state;
        this.data = data;
    }
}
