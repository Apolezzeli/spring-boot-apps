package ar.com.ada.second.tdvr.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AlbumDTO implements Serializable {

    private Long id;

    /**
     * @Pattern es la validacion con expresion regular, en este caso solo admite caracteres de la a-z A-Z,
     * espacios y numeros
     */
    @NotBlank(message = "is required")
    @Pattern(regexp = "^[0-9a-zA-ZáéíóúÁÉÍÓÚÜüñÑ\\s]*$", message = "title contains not allowed characters")
    private String title;

    /**
     * @JsonFormat es el formato en que se admite la fecha, en este caso es yyyy (año de 4 digitos). es caso tal que
     * se quiera otro formato, solo hay que inidcarlo (ver mas en https://www.baeldung.com/jackson-jsonformat)
     * @PastOrPresent es para validar que la fecha que se ingresa se una actual o pasada, no futura
     */
    @JsonFormat(pattern = "yyyy")
    @NotNull(message = "is required")
    @PastOrPresent(message = "the year must be past or present")
    private Date released;

    private ArtistDTO artist;

    private List<TrackDTO> tracks;

    public Boolean hasNullOrEmptyAttributes() {
        return title == null || title.trim().isEmpty()
                || released == null || artist == null;
    }
}

/*
{
    "title": "Like a Prayer",
    "released": "1989"
}
 */