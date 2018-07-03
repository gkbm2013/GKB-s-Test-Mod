package tinker_io.registry;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import tinker_io.item.TestItem;

public class ItemRegistry {

    public static TestItem testItem = new TestItem("test_item");

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(testItem);
    }

    public static void registerModels() {
        testItem.registerItemModel();
    }

//    private static ModelResourceLocation getModelResourceLocation(String name) {
//        return new ModelResourceLocation(TinkerIO.MOD_ID + ":" + name, "inventory");
//    }
}
