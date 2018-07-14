package tinker_io.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {
    public static void init(){
        GameRegistry.addSmelting(BlockRegistry.testBlock, new ItemStack(ItemRegistry.testItem), 0.7f);
    }
}
