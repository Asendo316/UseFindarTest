
package com.usefindar.app.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationObject {
    private Boolean isValid;
    private String validationMessage;
}
