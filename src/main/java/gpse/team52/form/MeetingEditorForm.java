package gpse.team52.form;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MeetingEditorForm.
 */
@NoArgsConstructor
public class MeetingEditorForm {
    @Getter
    @Setter
    @NotBlank
    private String name;
}
