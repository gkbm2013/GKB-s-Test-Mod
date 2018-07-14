package tinker_io.registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tinker_io.gui.GuiTestMachine;
import tinker_io.inventory.ContainerTestMachine;
import tinker_io.tileentity.TileEntityTestMachine;

import javax.annotation.Nullable;

public class GuiRegistry implements IGuiHandler {

    public static final int TEST_MACHINE = 0;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case TEST_MACHINE:
                return new ContainerTestMachine(player.inventory, (TileEntityTestMachine) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case TEST_MACHINE:
                return new GuiTestMachine((Container) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}
