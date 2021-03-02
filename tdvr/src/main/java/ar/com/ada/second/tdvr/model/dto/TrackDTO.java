package ar.com.ada.second.tdvr.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TrackDTO implements Serializable {
    private Long id;

    /**
     * @Pattern es la validacion con expresion regular, en este caso solo admite caracteres de la a-z A-Z,
     * espacios y numeros
     */
    @NotBlank(message = "is required")
    @Pattern(regexp = "^[0-9a-zA-ZáéíóúÁÉÍÓÚÜüñÑ\\s]*$", message = "title contains not allowed characters")
    private String title;

    private AlbumDTO album;

    private List<TrackDTO> tracks;

    public Boolean hasNullOrEmptyAttributes() {
        return title == null || title.trim().isEmpty()
                || album == null;
    }
}