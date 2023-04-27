package Library.dto.common;

import lombok.Data;

@Data
public class ValueDto<T> {
    private final T value;
}
