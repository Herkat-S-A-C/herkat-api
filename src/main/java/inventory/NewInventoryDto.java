package inventory;

import com.herkat.models.Inventory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewInventoryDto {

    @NotNull(message = "El ingreso del stock es obligatorio")
    private Integer stock;

    public static Inventory toEntity(NewInventoryDto newInventoryDto){
        return Inventory.newInventory(
                newInventoryDto.getStock(),
                null
        );
    }
}
