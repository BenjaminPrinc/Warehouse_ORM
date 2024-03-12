package at.bprinc.warehouse_orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/demo")
public class MainController {

    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @PostMapping(path = "/addWarehouse")
    public @ResponseBody String addNewWarehouse (@RequestParam String name, @RequestParam String address, @RequestParam Integer zip, @RequestParam String country, @RequestParam String city) {
        Warehouse w = new Warehouse();
        w.setWarehouseName(name);
        w.setWarehouseAddress(address);
        w.setWarehouseZip(zip);
        w.setWarehouseCountry(country);
        w.setWarehouseCity(city);
        warehouseRepository.save(w);
        return "Saved";
    }

    @PostMapping(path = "/addProducts")
    public @ResponseBody String addNewProduct (@RequestParam String name, @RequestParam String category, @RequestParam Integer quantity, @RequestParam String unit, @RequestParam Integer warehouseId) {
        Products p = new Products();
        p.setProductName(name);
        p.setProductCategory(category);
        p.setQuantity(quantity);
        p.setUnit(unit);
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse.isPresent()) {
            p.setWarehouse(warehouse.get());
            productsRepository.save(p);
            return "Saved product";
        } else {
            throw new RuntimeException("Warehouse not found");
        }

    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
}
