package inventory;

import com.herkat.models.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class InventoryDto {

    private Integer id;

    private Integer stock;

    public static InventoryDto fromEntity(Inventory inventory){
        return new InventoryDto(
                inventory.getId(),
                inventory.getStock()
        );
    }
}
