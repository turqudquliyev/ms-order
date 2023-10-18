package az.ingress.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public enum JsonNodeFieldName {
    CODE("code");
    String value;
}