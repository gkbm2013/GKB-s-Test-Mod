package tinker_io.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TestBlock extends BlockBase {

    public TestBlock(String name) {
        super(Material.ROCK, name);

        setHardness(3f);
        setResistance(5f);
    }
}
