package inventory;

import com.herkat.dtos.client.UpdateClientDto;
import com.herkat.models.Inventory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateInventoryDto {

    @NotNull(message = "El ingreso del stock es obligatorio")
    private Integer stock;

    public static Inventory updateEntity(UpdateInventoryDto dto, Inventory existingInventory){
        return new Inventory(
                existingInventory.getId(),
                dto.getStock() != null ? dto.getStock() : existingInventory.getStock(),
                existingInventory.getUpdatedAt()
        );
    }
}
