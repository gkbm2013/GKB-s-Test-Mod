package tinker_io.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import tinker_io.block.BlockTestMachine;
import tinker_io.block.TestBlock;

public class BlockRegistry {

    public static TestBlock testBlock = new TestBlock("test_block");
    public static BlockTestMachine testMachine = new BlockTestMachine("test_machine");

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                testBlock,
                testMachine
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                testBlock.createItemBlock(),
                testMachine.createItemBlock()
        );
    }

    public static void registerModels() {
        testBlock.registerItemModel(Item.getItemFromBlock(testBlock));
        testMachine.registerItemModel(Item.getItemFromBlock(testMachine));
    }
}
